package szs.findrefund.common.exception.income;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import szs.findrefund.common.enums.IncomeExceptionEnum;
import szs.findrefund.common.exception.custom.ScrapLoadingException;

@Slf4j
@RestControllerAdvice
public class IncomeExceptionHandler {

  /**
   * URL 스크랩 데이터가 존재하지 않거나 현재 서버와 통신중 일 경우
   */
  @ExceptionHandler(ScrapLoadingException.class)
  protected ResponseEntity<IncomeErrorResponse> handleScrapLoadingException() {
    log.error("handleScrapLoadingException: {}", IncomeExceptionEnum.SCRAP_LOADING_EXCEPTION.getMsg());
    IncomeErrorResponse incomeErrorResponse = IncomeErrorResponse.of(IncomeExceptionEnum.SCRAP_LOADING_EXCEPTION);
    return new ResponseEntity<>(incomeErrorResponse, HttpStatus.BAD_REQUEST);
  }

}
