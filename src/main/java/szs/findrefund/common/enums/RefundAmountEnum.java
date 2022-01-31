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
public enum RefundAmountEnum {

  총지급액_최소이하,
  총지급액_최소초과_최대이하,
  총지급액_최대초과,

  없음;

  public static RefundAmountEnum findRefundStandard(BigDecimal totalPaymentAmount) {
    if (totalPaymentAmount.compareTo(MIN_PAYMENT_AMOUNT) <= 0) {
      return Optional.ofNullable(총지급액_최소이하).orElse(없음);
    } else if (totalPaymentAmount.compareTo(MAX_PAYMENT_AMOUNT) <= 0) {
      return Optional.ofNullable(총지급액_최소초과_최대이하).orElse(없음);
    }

    return Optional.ofNullable(총지급액_최대초과).orElse(없음);
  }

}