package szs.findrefund.common.exception.user;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import szs.findrefund.common.enums.UserExceptionEnum;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserErrorResponse {

  private String errorCode;
  private String msg;


  private UserErrorResponse(UserExceptionEnum userExceptionEnum) {
    this.errorCode = userExceptionEnum.getErrorCode();
    this.msg = userExceptionEnum.getMsg();
  }

  public static UserErrorResponse of(UserExceptionEnum userExceptionEnum) {
    return new UserErrorResponse(userExceptionEnum);
  }

}
