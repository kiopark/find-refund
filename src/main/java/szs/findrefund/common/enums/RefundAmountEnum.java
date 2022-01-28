package szs.findrefund.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static szs.findrefund.common.Constants.RefundConst.*;

@Getter
@RequiredArgsConstructor
public enum RefundAmountEnum {

  총지급액_최소이하(-2),
  총지급액_최소초과_최대이하(0),
  총지급액_최대초과(2),

  없음(-1);

  private final int value;

  private static final Map<Integer, RefundAmountEnum> values =
      Collections.unmodifiableMap(Stream.of(values())
          .collect(Collectors.toMap(RefundAmountEnum::getValue, Function.identity())));

  public static RefundAmountEnum find(int value) {
    return Optional.ofNullable(values.get(value)).orElse(없음);
  }

  public static RefundAmountEnum findRefundStandard(BigDecimal totalPaymentAmount) {
    int resultCompare = totalPaymentAmount.compareTo(MIN_PAYMENT_AMOUNT) +
                        totalPaymentAmount.compareTo(MAX_PAYMENT_AMOUNT);
    return find(resultCompare);
  }

}