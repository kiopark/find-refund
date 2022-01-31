package szs.findrefund.common.exception.jwt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import szs.findrefund.common.enums.JwtExceptionEnum;
import szs.findrefund.common.exception.jwt.custom.JwtExpiredException;
import szs.findrefund.common.exception.jwt.custom.JwtTemperedException;
import szs.findrefund.common.exception.jwt.custom.JwtValidException;

@Slf4j
@RestControllerAdvice
public class JWTExceptionHandler {

  /**
   * 존재하지 않는 토큰 일 경우 발생
   */
  @ExceptionHandler(JwtValidException.class)
  protected ResponseEntity<JWTErrorResponse> handleJwtValidExceptionException(JwtValidException e) {
    log.error("handleJwtValidExceptionException: {}", JwtExceptionEnum.TOKEN_IS_NULL.getMsg());
    JWTErrorResponse jwtErrorResponse = JWTErrorResponse.of(JwtExceptionEnum.TOKEN_IS_NULL);
    return new ResponseEntity<>(jwtErrorResponse, HttpStatus.BAD_REQUEST);
  }

  /**
   * 만료된 토큰 일 경우 발생
   */
  @ExceptionHandler(JwtExpiredException.class)
  protected ResponseEntity<JWTErrorResponse> handleJwtExpiredException(JwtExpiredException e) {
    log.error("handleJwtExpiredException: {}", JwtExceptionEnum.TOKEN_EXPIRED.getMsg());
    JWTErrorResponse jwtErrorResponse = JWTErrorResponse.of(JwtExceptionEnum.TOKEN_EXPIRED);
    return new ResponseEntity<>(jwtErrorResponse, HttpStatus.BAD_REQUEST);
  }

  /**
   * 변조된 토큰 일 경우 발생
   */
  @ExceptionHandler(JwtTemperedException.class)
  protected ResponseEntity<JWTErrorResponse> handleJwtTemperedException(JwtTemperedException e) {
    log.error("handleJwtTemperedException: {}", JwtExceptionEnum.TOKEN_TAMPERED.getMsg());
    JWTErrorResponse jwtErrorResponse = JWTErrorResponse.of(JwtExceptionEnum.TOKEN_TAMPERED);
    return new ResponseEntity<>(jwtErrorResponse, HttpStatus.BAD_REQUEST);
  }
}
