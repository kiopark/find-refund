package szs.findrefund.service.income;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import szs.findrefund.common.exception.custom.ScrapLoadingException;
import szs.findrefund.domain.income.Income;
import szs.findrefund.domain.income.IncomeRepository;
import szs.findrefund.domain.scrapLog.ScrapLog;
import szs.findrefund.domain.scrapStatus.ScrapStatus;
import szs.findrefund.service.user.UserService;
import szs.findrefund.web.dto.refund.RefundResponseDto;
import szs.findrefund.web.dto.scrap.IncomeClassficationDto;
import szs.findrefund.web.dto.scrap.IncomeDetailDto;
import szs.findrefund.web.dto.scrap.ScrapRequestDto;
import szs.findrefund.web.dto.scrap.ScrapResponseDto;
import szs.findrefund.web.dto.user.UserInfoResponseDto;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;
import static szs.findrefund.common.Constants.RefundConst.*;
import static szs.findrefund.common.Constants.UrlConst.SCRAP_URL;
import static szs.findrefund.util.AESCryptoUtil.encrypt;

@ExtendWith(MockitoExtension.class)
class IncomeServiceTest {

  @InjectMocks
  private IncomeService incomeService;

  @Mock
  private UserService userService;

  @Mock
  private IncomeRepository incomeRepository;

  @Autowired
  private WebTestClient webTestClient;

  private final LocalDateTime ResDt = LocalDateTime.of(2022,12,1,2,24,0);
  private final LocalDateTime ReqDt = LocalDateTime.of(2022,12,1,2,24,59);

  @DisplayName("URL 스크랩 API WebClient 호출")
  @Test
  void URL_Scrap_Api_Call_Of_WebClient() throws Exception {

    // given
    final String accessToken = "eyJyZWdEYXRlIjoxNjQzNTI0OTUyMDM1LCJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6IjEiLCJpYXQiOjE2NDM1MjQ5NTIsImV4cCI6MTY0NDM4ODk1Mn0.M3kYZE7TULLg4yYZSTlB4soED2o_Rl_zBgJJwF_8VOI";
    ScrapRequestDto scrapRequestDto = scrapDto();
    given(userService.findMyInfoForUrlScrap(1L)).willReturn(scrapRequestDto);

    webTestClient.method(HttpMethod.POST)
                       .uri(SCRAP_URL)
                       .contentType(MediaType.APPLICATION_JSON)
                       .accept(MediaType.APPLICATION_JSON)
                       .bodyValue(scrapRequestDto)
                       .exchange()
                       .expectBody()
                       .consumeWith(result -> {
                         System.out.println(result.getResponseHeaders());
                         System.out.println(result.getResponseBody());
                       });

    // when
    Mono<ScrapResponseDto> scrapResponseDtoMono = incomeService.userScrapApiCall(accessToken);

    // then
    System.out.println("scrapResponseDtoMono = " + scrapResponseDtoMono);

  }

  @DisplayName("환급액 조회 실패")
  @Test
  void Find_Refund_Fail() throws Exception {
    // given
    final String accessToken = "eyJyZWdEYXRlIjoxNjQzNTI0OTUyMDM1LCJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6IjEiLCJpYXQiOjE2NDM1MjQ5NTIsImV4cCI6MTY0NDM4ODk1Mn0.M3kYZE7TULLg4yYZSTlB4soED2o_Rl_zBgJJwF_8VOI";
    final UserInfoResponseDto userDto = userDto();
    given(userService.findMyInfo(any())).willReturn(userDto);
    given(incomeRepository.findByRegNo(encrypt(userDto.getRegNo())))
                          .willThrow(new ScrapLoadingException());

    // when

    // then
    assertThatExceptionOfType(ScrapLoadingException.class)
        .isThrownBy(() -> incomeService.selectMyRefund(accessToken));
  }

  @DisplayName("환급액 조회 성공")
  @Test
  void Find_Refund_Success() throws Exception {
    // given
    final String accessToken = "eyJyZWdEYXRlIjoxNjQzNTI0OTUyMDM1LCJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6IjEiLCJpYXQiOjE2NDM1MjQ5NTIsImV4cCI6MTY0NDM4ODk1Mn0.M3kYZE7TULLg4yYZSTlB4soED2o_Rl_zBgJJwF_8VOI";
    final BigDecimal amount = BigDecimal.valueOf(2000000);
    final Income incomDto = incomDto();
    final UserInfoResponseDto userDto = userDto();
    given(userService.findMyInfo(any())).willReturn(userDto);
    given(incomeRepository.findByRegNo(encrypt(userDto.getRegNo())))
                          .willReturn(Optional.ofNullable(incomDto));

    // when
    RefundResponseDto myRefund = incomeService.selectMyRefund(accessToken);

    // then
    assertThat(myRefund.getName()).isEqualTo(userDto.getName());
    assertThat(myRefund.getLimitAmount()).isEqualTo(MAX_REFUND_AMOUNT);
    assertThat(myRefund.getDeductionAmount()).isEqualTo(
        DEDUCTION_AMOUNT.add((amount.subtract(TAX_AMOUNT))
                        .multiply(new BigDecimal(0.3))));
    assertThat(myRefund.getRefundAmount()).isEqualTo(MAX_REFUND_AMOUNT);
  }

  @DisplayName("[산출세액 130만원 이하] 공제액 계산")
  @Test
  void Deduction_First_Situation() throws Exception {
    // given
    final BigDecimal amount = BigDecimal.valueOf(1300000);

    // when
    Method privateMethod = incomeService.getClass()
        .getDeclaredMethod("taxDeductionCalc", BigDecimal.class);

    privateMethod.setAccessible(true);
    Object methodResult = privateMethod.invoke(incomeService, amount);

    // then
    assertThat(methodResult).isEqualTo(amount.multiply(new BigDecimal(0.55)));
  }

  @DisplayName("[산출세액 130만원 초과] 공제액 계산")
  @Test
  void Deduction_Second_Situation() throws Exception {
    // given
    final BigDecimal amount = BigDecimal.valueOf(1300001);

    // when
    Method privateMethod = incomeService.getClass()
        .getDeclaredMethod("taxDeductionCalc", BigDecimal.class);

    privateMethod.setAccessible(true);
    Object methodResult = privateMethod.invoke(incomeService, amount);

    // then
    assertThat(methodResult).isEqualTo(
            DEDUCTION_AMOUNT.add((amount.subtract(TAX_AMOUNT))
                            .multiply(new BigDecimal(0.3))));
  }

  @DisplayName("[3300만원 이하] 한도액 계산")
  @Test
  void Limit_First_Situation() throws Exception {
    // given
    final BigDecimal amount = BigDecimal.valueOf(33000000);

    // when
    Method privateMethod = incomeService.getClass()
        .getDeclaredMethod("taxLimitCalc", BigDecimal.class);

    privateMethod.setAccessible(true);
    Object methodResult = privateMethod.invoke(incomeService, amount);

    // then
    assertThat(methodResult).isEqualTo(MAX_REFUND_AMOUNT);
  }

  @DisplayName("[3300만원 초과 7000만원 이하] 한도액 계산")
  @Test
  void Limit_Second_Situation() throws Exception {
    // given
    final BigDecimal amount = BigDecimal.valueOf(33000001);

    // when
    Method privateMethod = incomeService.getClass()
        .getDeclaredMethod("taxLimitCalc", BigDecimal.class);

    privateMethod.setAccessible(true);
    Object methodResult = privateMethod.invoke(incomeService, amount);

    // then
    assertThat(methodResult).isEqualTo(
        MAX_REFUND_AMOUNT.subtract((amount.subtract(MIN_PAYMENT_AMOUNT))
            .multiply(new BigDecimal(0.008))));
  }

  @DisplayName("[3300만원 초과 7000만원 이하] && [공제요건 66만원보다 적은경우] 한도액 계산")
  @Test
  void Limit_Third_Situation() throws Exception {
    // given
    final BigDecimal amount = BigDecimal.valueOf(50000000);

    // when
    Method privateMethod = incomeService.getClass()
        .getDeclaredMethod("taxLimitCalc", BigDecimal.class);

    privateMethod.setAccessible(true);
    Object methodResult = privateMethod.invoke(incomeService, amount);

    // then
    assertThat(methodResult).isEqualTo(MIDDLE_REFUND_AMOUNT);
  }

  @DisplayName("[7000만원 초과] 한도액 계산")
  @Test
  void Limit_Forth_Situation() throws Exception {
    // given
    final BigDecimal amount = BigDecimal.valueOf(70000001);

    // when
    Method privateMethod = incomeService.getClass()
        .getDeclaredMethod("taxLimitCalc", BigDecimal.class);

    privateMethod.setAccessible(true);
    Object methodResult = privateMethod.invoke(incomeService, amount);

    // then
    assertThat(methodResult).isEqualTo(
        MIDDLE_REFUND_AMOUNT.subtract((amount.subtract(MAX_PAYMENT_AMOUNT))
            .divide(new BigDecimal(2))));
  }

  @DisplayName("[7000만원 초과] && [공제요건 50만원보다 적은경우] 한도액 계산")
  @Test
  void Limit_Fifth_Situation() throws Exception {
    // given
    final BigDecimal amount = BigDecimal.valueOf(100000000);

    // when
    Method privateMethod = incomeService.getClass()
        .getDeclaredMethod("taxLimitCalc", BigDecimal.class);

    privateMethod.setAccessible(true);
    Object methodResult = privateMethod.invoke(incomeService, amount);

    // then
    assertThat(methodResult).isEqualTo(MIN_REFUND_AMOUNT);
  }

  @DisplayName("스크랩한 객체 생성")
  private ScrapRequestDto scrapDto() {
    return ScrapRequestDto.builder()
                          .name("홍길동")
                          .regNo("860824-1655068")
                          .build();
  }

  @DisplayName("회원 객체 생성")
  private UserInfoResponseDto userDto() {
    return UserInfoResponseDto.builder()
                              .userId("userId")
                              .name("홍길동")
                              .regNo("860824-1655068")
                              .build();
  }

  @DisplayName("환급 객체 생성")
  private Income incomDto() throws Exception {
    return Income.builder()
                 .incomeDetailDto(incomeDetailDto())
                 .incomeClassficationDto(incomeClassficationDto())
                 .scrapStatus(scrapStatusDto())
                 .scrapLog(scrapLogDto())
                 .build();
  }

  @DisplayName("환급 세부정보 객체 생성")
  private IncomeDetailDto incomeDetailDto() {
    return IncomeDetailDto.builder()
                          .incomeType("급여")
                          .totalPaymentAmount(BigDecimal.valueOf(24000000))
                          .businessStartDate("2020.10.03")
                          .enterpriseName("(주)활빈당")
                          .userName("홍길동")
                          .regNo("860824-1655068")
                          .paymentDate("2020.11.02")
                          .businessEndDate("2020.11.02")
                          .incomeDivision("근로소득(연간)")
                          .enterpriseRegNo("012-34-56789")
                          .build();
  }

  @DisplayName("산출세액 객체 생성")
  private IncomeClassficationDto incomeClassficationDto() {
    return IncomeClassficationDto.builder()
                                 .classfication("산출세액")
                                 .usedTotalAmount(BigDecimal.valueOf(2000000))
                                 .build();
  }

  @DisplayName("스크랩 상태정보 객체 생성")
  private ScrapStatus scrapStatusDto() {
    return ScrapStatus.builder()
                      .errMsg("")
                      .company("삼쩜삼")
                      .svcCd("test01")
                      .userId("1")
                      .build();
  }

  @DisplayName("스크랩 로그정보 객체 생성")
  private ScrapLog scrapLogDto() {
    return ScrapLog.builder()
                   .appVer("2021112501")
                   .hostNm("szs-codetest")
                   .workerResDt(ResDt)
                   .workerReqDt(ReqDt)
                   .build();
  }
}