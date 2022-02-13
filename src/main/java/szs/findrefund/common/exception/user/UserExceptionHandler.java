package szs.findrefund.common.exception.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import szs.findrefund.common.enums.UserExceptionEnum;
import szs.findrefund.common.exception.user.custom.*;

@Slf4j
@RestControllerAdvice
public class UserExceptionHandler {

  /**
   * 존재하지 않는 회원 일 경우 발생
   */
  @ExceptionHandler(UserNotFoundException.class)
  protected ResponseEntity<UserErrorResponse> handleUserNotFoundException() {
    log.error("handleUserNotFoundException: {}", UserExceptionEnum.USER_NOT_FOUND.getMsg());
    UserErrorResponse userErrorResponse = UserErrorResponse.of(UserExceptionEnum.USER_NOT_FOUND);
    return new ResponseEntity<>(userErrorResponse, HttpStatus.BAD_REQUEST);
  }

  /**
   * 가입 가능한 회원이 아닐 경우 발생
   */
  @ExceptionHandler(NonAvailableUserException.class)
  protected ResponseEntity<UserErrorResponse> handleNonAvailableUserException() {
    log.error("handleNonAvailableUserException: {}", UserExceptionEnum.NON_AVAILABLE_USER.getMsg());
    UserErrorResponse userErrorResponse = UserErrorResponse.of(UserExceptionEnum.NON_AVAILABLE_USER);
    return new ResponseEntity<>(userErrorResponse, HttpStatus.BAD_REQUEST);
  }

  /**
   * 회원가입시 이미 존재하는 회원 일 경우 발생
   */
  @ExceptionHandler(ValidDuplicatedUserException.class)
  protected ResponseEntity<UserErrorResponse> handleValidDuplicatedUserException() {
    log.error("handleValidDuplicatedUserException: {}", UserExceptionEnum.VALIDATED_DUPLICATED_USERS.getMsg());
    UserErrorResponse userErrorResponse = UserErrorResponse.of(UserExceptionEnum.VALIDATED_DUPLICATED_USERS);
    return new ResponseEntity<>(userErrorResponse, HttpStatus.BAD_REQUEST);
  }

  /**
   * 회원가입시 이미 존재하는 아이디 일 경우 발생
   */
  @ExceptionHandler(ValidDuplicatedIdException.class)
  protected ResponseEntity<UserErrorResponse> handleValidDuplicatedIdException() {
    log.error("handleValidDuplicatedIdException: {}", UserExceptionEnum.VALIDATED_DUPLICATED_ID.getMsg());
    UserErrorResponse userErrorResponse = UserErrorResponse.of(UserExceptionEnum.VALIDATED_DUPLICATED_ID);
    return new ResponseEntity<>(userErrorResponse, HttpStatus.BAD_REQUEST);
  }

  /**
   * 회원가입시 이미 존재하는 가입정보 일 경우 발생
   */
  @ExceptionHandler(PassWordNotMatchException.class)
  protected ResponseEntity<UserErrorResponse> handlePasswordNotMatchException() {
    log.error("handlePasswordNotMatchException: {}", UserExceptionEnum.PASSWORD_NOT_MATCHED.getMsg());
    UserErrorResponse userErrorResponse = UserErrorResponse.of(UserExceptionEnum.PASSWORD_NOT_MATCHED);
    return new ResponseEntity<>(userErrorResponse, HttpStatus.BAD_REQUEST);
  }

  /**
   * 회원가입시 주민등록번호 패턴 불일치 일 경우 발생
   */
  @ExceptionHandler(RegNoNotMatchException.class)
  protected ResponseEntity<UserErrorResponse> handleRegNoNotMatchException() {
    log.error("handleRegNoNotMatchException: {}", UserExceptionEnum.REGNO_NOT_MATCHED.getMsg());
    UserErrorResponse userErrorResponse = UserErrorResponse.of(UserExceptionEnum.REGNO_NOT_MATCHED);
    return new ResponseEntity<>(userErrorResponse, HttpStatus.BAD_REQUEST);
  }

}
