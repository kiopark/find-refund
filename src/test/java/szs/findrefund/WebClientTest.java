package szs.findrefund;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.BodySpec;
import org.springframework.test.web.reactive.server.WebTestClient.RequestBodySpec;
import szs.findrefund.service.income.IncomeService;
import szs.findrefund.web.dto.refund.RefundResponseDto;
import szs.findrefund.web.dto.scrap.ScrapRequestDto;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static szs.findrefund.common.Constants.RefundConst.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureWebTestClient
public class WebClientTest {

  @MockBean
  private IncomeService incomeService;

  @Autowired
  private WebTestClient webTestClient;

  @DisplayName("[Controller] URL 스크랩 API WebClient 호출")
  @Test
  void URL_Scrap_Api_Call_Of_WebClient() throws Exception {

    // given
    final String accessToken = "eyJyZWdEYXRlIjoxNjQzNTI0OTUyMDM1LCJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6IjEiLCJpYXQiOjE2NDM1MjQ5NTIsImV4cCI6MTY0NDM4ODk1Mn0.M3kYZE7TULLg4yYZSTlB4soED2o_Rl_zBgJJwF_8VOI";
    doNothing().when(incomeService).saveScrapData(any());

    // when
    RequestBodySpec bodySpec = webTestClient
                                    .post()
                                    .uri("/api/szs/scrap")
                                    .header(HttpHeaders.AUTHORIZATION, accessToken);

    // then
    bodySpec.exchange()
            .expectBody(String.class).isEqualTo("API 요청 완료!");
  }

  @DisplayName("스크랩한 객체 생성")
  private ScrapRequestDto scrapDto() {
    return ScrapRequestDto.builder()
        .name("홍길동")
        .regNo("860824-1655068")
        .build();
  }

  @DisplayName("회원 회원가입 객체 생성")
  private RefundResponseDto responseDto() {
    return RefundResponseDto.builder()
        .name("홍길동")
        .limitAmount(MIN_PAYMENT_AMOUNT)
        .deductionAmount(MIDDLE_REFUND_AMOUNT)
        .refundAmount(MAX_REFUND_AMOUNT)
        .build();
  }

  @DisplayName("[POST] URL 스크랩 API 호출")
  private BodySpec requestPostUrlScrap(String accessToken) {
    return webTestClient.post()
                        .uri("/api/szs/scrap")
                        .header(HttpHeaders.AUTHORIZATION, accessToken)
                        .exchange()
                        .expectBody(String.class);
  }

}
