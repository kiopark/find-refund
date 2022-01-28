package szs.findrefund.common.exception.jwt;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import szs.findrefund.common.enums.JwtExceptionEnum;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JWTErrorResponse {

  private String errorCode;
  private String msg;


  private JWTErrorResponse(JwtExceptionEnum jwtExceptionEnum) {
    this.errorCode = jwtExceptionEnum.getErrorCode();
    this.msg = jwtExceptionEnum.getMsg();
  }

  public static JWTErrorResponse of(JwtExceptionEnum jwtExceptionEnum) {
    return new JWTErrorResponse(jwtExceptionEnum);
  }

}
