package szs.findrefund.common.exception.refund;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import szs.findrefund.common.enums.UserExceptionEnum;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefundErrorResponse {

  private String errorCode;
  private String msg;


  private RefundErrorResponse(UserExceptionEnum userExceptionEnum) {
    this.errorCode = userExceptionEnum.getErrorCode();
    this.msg = userExceptionEnum.getMsg();
  }

  public static RefundErrorResponse of(UserExceptionEnum userExceptionEnum) {
    return new RefundErrorResponse(userExceptionEnum);
  }

}
