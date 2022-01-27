package szs.findrefund.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import szs.findrefund.common.enums.UserExceptionEnum;
import szs.findrefund.common.exception.custom.*;

@Slf4j
@RestControllerAdvice
public class CommonExceptionHandler {

  /**
   * 존재하지 않는 회원 일 경우 발생
   */
  @ExceptionHandler(UserNotFoundException.class)
  protected ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException e) {
    log.error("handleUserNotFoundException", UserExceptionEnum.USER_NOT_FOUND.getMsg());
    ErrorResponse errorResponse = ErrorResponse.of(UserExceptionEnum.USER_NOT_FOUND);
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  /**
   * 가입 가능한 회원이 아닐 경우 발생
   */
  @ExceptionHandler(NonAvailableUserException.class)
  protected ResponseEntity<ErrorResponse> handleNonAvailableUserException(NonAvailableUserException e) {
    log.error("handleNonAvailableUserException", UserExceptionEnum.NON_AVAILABLE_USER.getMsg());
    ErrorResponse errorResponse = ErrorResponse.of(UserExceptionEnum.NON_AVAILABLE_USER);
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  /**
   * 회원가입시 이미 존재하는 가입정보 일 경우 발생
   */
  @ExceptionHandler(ValidDuplicatedUserException.class)
  protected ResponseEntity<ErrorResponse> handleValidDuplicatedUserException(ValidDuplicatedUserException e) {
    log.error("handleValidDuplicatedUserException", UserExceptionEnum.VALIDATED_DUPLICATED_USERS.getMsg());
    ErrorResponse errorResponse = ErrorResponse.of(UserExceptionEnum.VALIDATED_DUPLICATED_USERS);
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  /**
   * 회원가입시 이미 존재하는 가입정보 일 경우 발생
   */
  @ExceptionHandler(PassWordNotMatchException.class)
  protected ResponseEntity<ErrorResponse> handlePasswordNotMatchException(PassWordNotMatchException e) {
    log.error("handlePasswordNotMatchException", UserExceptionEnum.PASSWORD_NOT_MATCHED.getMsg());
    ErrorResponse errorResponse = ErrorResponse.of(UserExceptionEnum.PASSWORD_NOT_MATCHED);
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  /**
   * 회원가입시 주민등록번호 패턴 불일치 일 경우 발생
   */
  @ExceptionHandler(RegNoNotMatchException.class)
  protected ResponseEntity<ErrorResponse> handleRegNoNotMatchException(RegNoNotMatchException e) {
    log.error("handleRegNoNotMatchException", UserExceptionEnum.REGNO_NOT_MATCHED.getMsg());
    ErrorResponse errorResponse = ErrorResponse.of(UserExceptionEnum.REGNO_NOT_MATCHED);
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

}
