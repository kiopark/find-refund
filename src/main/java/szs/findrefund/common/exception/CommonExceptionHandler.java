package szs.findrefund.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import szs.findrefund.common.enums.CommonExceptionEnum;

@Slf4j
@RestControllerAdvice
public class CommonExceptionHandler {

  /**
   * @Validated 유효성검사에 binding 실패할 경우 발생
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  protected ResponseEntity<CommonErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
    log.error("handleMethodArgumentNotValidException: {}", CommonExceptionEnum.INVALID_INPUT_VALUE.getMsg());
    CommonErrorResponse errorResponse = CommonErrorResponse.of(CommonExceptionEnum.INVALID_INPUT_VALUE);
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  /**
   * 지원하지 않는 HTTP method 호출 할 경우 발생
   */
  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  protected ResponseEntity<CommonErrorResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
    log.error("handleHttpRequestMethodNotSupportedException: {}", CommonExceptionEnum.METHOD_NOT_ALLOWED.getMsg());
    CommonErrorResponse errorResponse = CommonErrorResponse.of(CommonExceptionEnum.METHOD_NOT_ALLOWED);
    return new ResponseEntity<>(errorResponse, HttpStatus.METHOD_NOT_ALLOWED);
  }

}
