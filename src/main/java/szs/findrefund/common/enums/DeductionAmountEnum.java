package szs.findrefund.common.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.Optional;

import static szs.findrefund.common.Constants.RefundConst.*;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@RequiredArgsConstructor
public enum DeductionAmountEnum {

  산출세액_기준_이하,
  산출세액_기준_초과,

  없음;

  public static DeductionAmountEnum findDeductionStandard(BigDecimal totalPaymentAmount) {
    if (totalPaymentAmount.compareTo(TAX_AMOUNT) <= 0) {
      return Optional.ofNullable(산출세액_기준_이하).orElse(없음);
    }
    return Optional.ofNullable(산출세액_기준_초과).orElse(없음);
  }

}