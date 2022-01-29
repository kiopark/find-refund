package szs.findrefund.web;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import szs.findrefund.common.enums.CommonExceptionEnum;
import szs.findrefund.config.WebConfig;
import szs.findrefund.service.income.IncomeService;
import szs.findrefund.web.dto.refund.RefundResponseDto;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static szs.findrefund.common.Constants.RefundConst.*;

@WebMvcTest(controllers = IncomeApiController.class,
            excludeFilters = {
            @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
                                  classes = WebConfig.class)
})
class IncomeApiControllerTest {

  @MockBean
  private IncomeService incomeService;

  @Autowired
  private MockMvc mvc;

  @DisplayName("URL 스크랩 API 요청")
  @Test
  void URL_SCRAP_API_CALL() throws Exception {
    // given
    final String uri = "scrap";
    final String accessToken = "accessToken";
    doNothing().when(incomeService).saveScrapData(any());

    // when
    ResultActions resultActions = requestPostAccessToken(accessToken, uri);

    // then
    resultActions
        .andExpect(status().isOk())
        .andExpect(content().string("API 요청 완료!"))
        .andDo(print());
  }

  @DisplayName("환급 정보 조회")
  @Test
  void FIND_REFUND() throws Exception {
    // given
    final String uri = "refund";
    final String accessToken = "accessToken";
    given(incomeService.selectMyRefund(any())).willReturn(requestDto());

    // when
    ResultActions resultActions = requestGetAccessToken(accessToken, uri);

    // then
    resultActions.andExpect(status().isOk())
        .andExpect(jsonPath("$.이름", is("홍길동")))
        .andExpect(jsonPath("$.한도").value(MIN_PAYMENT_AMOUNT))
        .andExpect(jsonPath("$.공제액").value(MIDDLE_REFUND_AMOUNT))
        .andExpect(jsonPath("$.환급액").value(MAX_REFUND_AMOUNT))
        .andDo(print());
  }

  @DisplayName("잘못된 HTTP Method 요청")
  @Test
  void FIND_REFUND_Not_Allowed_Method() throws Exception {
    // given
    final String uri = "refund";
    final String accessToken = "accessToken";
    given(incomeService.selectMyRefund(any())).willReturn(requestDto());

    // when
    ResultActions resultActions = requestPostAccessToken(accessToken, uri);

    // then
    resultActions
        .andExpect(status().isMethodNotAllowed())
        .andExpect(jsonPath("msg").value(CommonExceptionEnum.METHOD_NOT_ALLOWED.getMsg()))
        .andDo(print());
  }

  @DisplayName("회원 회원가입 객체 생성")
  private RefundResponseDto requestDto() {
    return RefundResponseDto.builder()
                            .name("홍길동")
                            .limitAmount(MIN_PAYMENT_AMOUNT)
                            .deductionAmount(MIDDLE_REFUND_AMOUNT)
                            .refundAmount(MAX_REFUND_AMOUNT)
                            .build();
  }

  @DisplayName("[GET] 환급정보 조회")
  private ResultActions requestGetAccessToken(String accessToken, String uri) throws Exception {
    return mvc.perform(get("/api/szs/" + uri)
        .header(HttpHeaders.AUTHORIZATION, accessToken));
  }

  @DisplayName("[POST] 환급정보 조회")
  private ResultActions requestPostAccessToken(String accessToken, String uri) throws Exception {
    return mvc.perform(post("/api/szs/" + uri)
        .header(HttpHeaders.AUTHORIZATION, accessToken));
  }

}