package szs.findrefund.common.exception.jwt.custom;

import szs.findrefund.common.enums.JwtExceptionEnum;

public class JwtValidException extends RuntimeException {

  private JwtExceptionEnum exception;

  public JwtValidException(JwtExceptionEnum exceptionEnum) {
    super(exceptionEnum.getMsg());
    this.exception = exceptionEnum;
  }
}
