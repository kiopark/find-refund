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

import static szs.findrefund.common.Constants.RefundConst.TAX_AMOUNT;

@Getter
@RequiredArgsConstructor
public enum DeductionAmountEnum {

  산출세액_이하(0),
  산출세액_초과(1),

  없음(-1);

  private final int value;

  private static final Map<Integer, DeductionAmountEnum> values =
      Collections.unmodifiableMap(Stream.of(values())
          .collect(Collectors.toMap(DeductionAmountEnum::getValue, Function.identity())));

  public static DeductionAmountEnum find(int value) {
    return Optional.ofNullable(values.get(value)).orElse(없음);
  }

  public static DeductionAmountEnum findDeductionStandard(BigDecimal totalPaymentAmount) {
    int resultCompare = totalPaymentAmount.compareTo(TAX_AMOUNT);
    return find(resultCompare);
  }

}