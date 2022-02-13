package szs.findrefund.service.income;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import szs.findrefund.common.enums.DeductionAmountEnum;
import szs.findrefund.common.enums.RefundAmountEnum;
import szs.findrefund.common.exception.custom.ScrapLoadingException;
import szs.findrefund.domain.income.Income;
import szs.findrefund.domain.income.IncomeRepository;
import szs.findrefund.domain.scrapLog.ScrapLog;
import szs.findrefund.domain.scrapStatus.ScrapStatus;
import szs.findrefund.service.user.UserService;
import szs.findrefund.util.JWTUtil;
import szs.findrefund.web.dto.refund.RefundResponseDto;
import szs.findrefund.web.dto.scrap.*;
import szs.findrefund.web.dto.user.UserInfoResponseDto;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.Optional;

import static szs.findrefund.common.Constants.RefundConst.*;
import static szs.findrefund.common.Constants.UrlConst.SCRAP_URL;
import static szs.findrefund.util.AESCryptoUtil.encrypt;

@Slf4j
@RequiredArgsConstructor
@Service
public class IncomeService {

  private final WebClient webClient;
  private final UserService userService;
  private final IncomeRepository incomeRepository;
  private final ApplicationContext applicationContext;

  /* 캐시 작동을 위한 self-invocation */
  private IncomeService self;

  @PostConstruct
  private void init() {
    self = (IncomeService) applicationContext.getBean("incomeService");
  }

  /**
   * 환급액 조회 캐시 초기화 (self.clearCache 로 호출)
   */
  @CacheEvict(value = "accessToken", key = "#accessToken")
  public void clearCache(String accessToken){
    log.debug("환급액 조회 캐시 초기화");
  }

  /**
   * 스크랩 한 유저 정보 저장
   */
  @Transactional
  @Cacheable(value = "accessToken", key = "#accessToken")
  public void saveScrapData(String accessToken) throws Exception {
    userScrapApiCall(accessToken)
        .subscribe(responseDto -> {
          try {
            Income incomeInfo = dtoMatchEntity(responseDto);
            Optional<Income> findIncome = incomeRepository.findByRegNo(incomeInfo.getRegNo());

            if (findIncome.isPresent()) {
              incomeRepository.delete(findIncome.get());
            }
            incomeRepository.save(incomeInfo);
          } catch (Exception e) {
            e.printStackTrace();
          } finally {
            self.clearCache(accessToken);
          }
        });
  }

  /**
   * WebClient 를 통해 스크랩 URL 호출
   */
  public Mono<ScrapResponseDto> userScrapApiCall(String accessToken) throws Exception {
    Long idFromToken = JWTUtil.getIdFromToken(accessToken);
    ScrapRequestDto requestDto = userService.findMyInfoForUrlScrap(idFromToken);

    return webClient.mutate()
                    .build()
                    .post()
                    .uri(SCRAP_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .bodyValue(requestDto)
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() ||
                                        status.is5xxServerError(),
                              client -> client.bodyToMono(String.class)
                                              .map(RuntimeException::new))
                    .bodyToMono(ScrapResponseDto.class);
  }


  /**
   * URL 스크랩 결과 정보를 Entity 로 매칭
   */
  private Income dtoMatchEntity(ScrapResponseDto responseDto) throws Exception {
    JsonListDto jsonList = responseDto.getJsonList();

    IncomeDetailDto incomeDetailDto =
        jsonList
            .getScrap001()
            .stream()
            .findFirst()
            .orElse(null);

    IncomeClassficationDto incomeClassficationDto =
        jsonList
            .getScrap002()
            .stream()
            .findFirst()
            .orElse(null);

    ScrapStatus scrapStatus = ScrapStatus.builder()
        .errMsg(jsonList.getErrMsg())
        .company(jsonList.getCompany())
        .svcCd(jsonList.getSvcCd())
        .userId(jsonList.getUserId())
        .build();

    ScrapLog scrapLog = ScrapLog.builder()
        .appVer(responseDto.getAppVer())
        .hostNm(responseDto.getHostNm())
        .workerReqDt(responseDto.getWorkerReqDt())
        .workerResDt(responseDto.getWorkerResDt())
        .build();

    return Income.builder()
        .incomeDetailDto(incomeDetailDto)
        .incomeClassficationDto(incomeClassficationDto)
        .scrapStatus(scrapStatus)
        .scrapLog(scrapLog)
        .build();
  }

  /**
   * 환급액 조회
   */
  @Transactional(readOnly = true)
  public RefundResponseDto selectMyRefund(String accessToken) throws Exception {
    Long idFromToken = JWTUtil.getIdFromToken(accessToken);
    UserInfoResponseDto myInfo = userService.findMyInfo(idFromToken);
    Income findIncome = incomeRepository.findByRegNo(encrypt(myInfo.getRegNo()))
                                        .orElseThrow(ScrapLoadingException::new);

    RefundResponseDto refundResponseDto = calcRefund(findIncome);
    return refundResponseDto;
  }

  /**
   * 환급액 계산
   */
  private RefundResponseDto calcRefund(Income findIncome) {
    /* 총 지급액 */
    BigDecimal totalPaymentAmount = findIncome.getTotalPaymentAmount();
    /* 한도 */
    BigDecimal limitAmount = taxLimitCalc(totalPaymentAmount);
    /* 공제액 */
    BigDecimal deductionAmount = taxDeductionCalc(findIncome.getIncomeClassfication().getUsedTotalAmount());
    /* 환급액 */
    BigDecimal refundAmount = limitAmount.min(deductionAmount);

    return RefundResponseDto.builder()
                            .name(findIncome.getUserName())
                            .limitAmount(limitAmount)
                            .deductionAmount(deductionAmount)
                            .refundAmount(refundAmount)
                            .build();
  }

  /**
   *  공제액 계산
   */
  private BigDecimal taxDeductionCalc(BigDecimal amount) {
    BigDecimal resultAmount = BigDecimal.ZERO;
    switch (DeductionAmountEnum.findDeductionStandard(amount)) {
      case 산출세액_기준_이하 :
        resultAmount = amount.multiply(new BigDecimal(0.55));
        break;
      case 산출세액_기준_초과 :
        resultAmount = DEDUCTION_AMOUNT.add((amount.subtract(TAX_AMOUNT)).multiply(new BigDecimal(0.3)));
        break;
    }
    return resultAmount;
  }

  /**
   * 한도액 계산
   */
  public static BigDecimal taxLimitCalc(BigDecimal amount) {
    BigDecimal calcAmount = BigDecimal.ZERO;
    BigDecimal resultAmount = MAX_REFUND_AMOUNT;

    switch (RefundAmountEnum.findRefundStandard(amount)) {
      case 총지급액_최소초과_최대이하 :
             calcAmount = MAX_REFUND_AMOUNT.subtract((amount.subtract(MIN_PAYMENT_AMOUNT))
                                           .multiply(new BigDecimal(0.008)));

             resultAmount = calcAmount.compareTo(MIDDLE_REFUND_AMOUNT) < 0 ? MIDDLE_REFUND_AMOUNT : calcAmount;
        break;
      case 총지급액_최대초과 :
             calcAmount = MIDDLE_REFUND_AMOUNT.subtract((amount.subtract(MAX_PAYMENT_AMOUNT))
                                              .divide(new BigDecimal(2)));

             resultAmount = calcAmount.compareTo(MIN_REFUND_AMOUNT) < 0 ? MIN_REFUND_AMOUNT : calcAmount;
        break;
    }
    return resultAmount;
  }

}