package szs.findrefund.service.refund;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import szs.findrefund.common.Constants;
import szs.findrefund.common.enums.RefundAmountEnum;
import szs.findrefund.common.exception.custom.UserNotFoundException;
import szs.findrefund.domain.refund.Refund;
import szs.findrefund.domain.refund.RefundRepository;
import szs.findrefund.util.JWTUtil;
import szs.findrefund.web.dto.scrap.IncomeDetailDto;
import szs.findrefund.web.dto.scrap.ScrapRequestDto;
import szs.findrefund.web.dto.scrap.ScrapResponseDto;

import java.math.BigDecimal;

import static szs.findrefund.common.Constants.JwtConst.NAME;
import static szs.findrefund.common.Constants.JwtConst.REG_NO;
import static szs.findrefund.common.Constants.UrlConst.SCRAP_URL;

@Service
@RequiredArgsConstructor
public class RefundService {

  private final WebClient webClient;
  private final RefundRepository refundRepository;

  /**
   * 스크랩 한 유저 정보 저장
   */
  @Transactional
  public void saveScrapData(String accessToken) {
    userScrapApiCall(accessToken)
        .subscribe(responseDto -> {
          Refund refund = new Refund();
          IncomeDetailDto incomeDetailDto = responseDto.getJsonList()
                                                       .getScrap001()
                                                       .stream()
                                                       .findFirst()
                                                       .orElse(null);
          incomeDetailDto.getRegNo();
          refundRepository.save(refund);
        });
  }

  /**
   * WebClient 를 통해 스크랩 URL 호출
   */
  public Mono<ScrapResponseDto> userScrapApiCall(String accessToken) {
    Claims claimsFromToken = JWTUtil.getClaimsFromToken(accessToken);
    String name = (String) claimsFromToken.get(NAME);
    String regNo = (String) claimsFromToken.get(REG_NO);
    ScrapRequestDto requestDto = ScrapRequestDto.builder()
        .name(name)
        .regNo(regNo)
        .build();

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
                .map(body -> new RuntimeException(body)))
        .bodyToMono(ScrapResponseDto.class);
  }

  /**
   * 환급액 조회
   */
  public void selectMyRefund(String jwtToken) {
    Long idFromToken = JWTUtil.getIdFromToken(jwtToken);
    Refund findRefund = refundRepository.findById(idFromToken)
                                        .orElseThrow(UserNotFoundException::new);
    calcRefund(findRefund);
  }

  /**
   * 환급액 계산
   */
  private BigDecimal calcRefund(Refund findRefund) {
    BigDecimal totalPaymentAmount = findRefund.getTotalPaymentAmount();
    BigDecimal taxCreditAmount = taxCredit(totalPaymentAmount);
    BigDecimal taxCreditLimitAmount = taxCreditLimit(totalPaymentAmount);

    return taxCreditAmount.min(taxCreditLimitAmount);
  }

  /**
   * 환급액 조회
   */
  private BigDecimal taxCredit(BigDecimal totalPaymentAmount) {
    BigDecimal resultAmount = Constants.RefundConst.BASE_REFUND_AMOUNT;
    int refundType = totalPaymentAmount.compareTo(Constants.RefundConst.MIN_PAYMENT_AMOUNT) +
                     totalPaymentAmount.compareTo(Constants.RefundConst.MAX_PAYMENT_AMOUNT);

    switch (RefundAmountEnum.find(refundType)) {
      case 총지급액_최소초과_최대이하 : resultAmount = BigDecimal.valueOf(1234);
        break;
      case 총지급액_최대초과 : resultAmount = BigDecimal.valueOf(740000);
        break;
    }

    return resultAmount;
  }

  /**
   * 환급액 조회
   */
  private BigDecimal taxCreditLimit(BigDecimal totalPaymentAmount) {
    return BigDecimal.ZERO;
  }

}