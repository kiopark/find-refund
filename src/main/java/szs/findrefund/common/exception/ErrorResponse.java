package szs.findrefund.common.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import szs.findrefund.common.enums.UserExceptionEnum;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {

  private String errorCode;
  private String msg;


  private ErrorResponse(UserExceptionEnum userExceptionEnum) {
    this.errorCode = userExceptionEnum.getErrorCode();
    this.msg = userExceptionEnum.getMsg();
  }

  public static ErrorResponse of(UserExceptionEnum userExceptionEnum) {
    return new ErrorResponse(userExceptionEnum);
  }

}
