package szs.findrefund.common.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import szs.findrefund.common.enums.CommonExceptionEnum;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommonErrorResponse {

  private String errorCode;
  private String msg;

  private CommonErrorResponse(CommonExceptionEnum commonExceptionEnum) {
    this.errorCode = commonExceptionEnum.getErrorCode();
    this.msg = commonExceptionEnum.getMsg();
  }

  public static CommonErrorResponse of(CommonExceptionEnum commonExceptionEnum) {
    return new CommonErrorResponse(commonExceptionEnum);
  }

}
