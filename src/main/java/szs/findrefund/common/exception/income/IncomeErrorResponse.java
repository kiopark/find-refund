package szs.findrefund.common.exception.income;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import szs.findrefund.common.enums.IncomeExceptionEnum;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class IncomeErrorResponse {

  private String errorCode;
  private String msg;


  private IncomeErrorResponse(IncomeExceptionEnum incomeExceptionEnum) {
    this.errorCode = incomeExceptionEnum.getErrorCode();
    this.msg = incomeExceptionEnum.getMsg();
  }

  public static IncomeErrorResponse of(IncomeExceptionEnum incomeExceptionEnum) {
    return new IncomeErrorResponse(incomeExceptionEnum);
  }

}
