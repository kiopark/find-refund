package szs.findrefund.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum RefundAmountEnum {

  총지급액_최소미만(-2),
  총지급액_최소초과_최대이하(0),
  총지급액_최대초과(2),

  없음(0);

  private final int value;

  private static final Map<Integer, RefundAmountEnum> values =
      Collections.unmodifiableMap(Stream.of(values())
          .collect(Collectors.toMap(RefundAmountEnum::getValue, Function.identity())));

  public static RefundAmountEnum find(int value) {
    return Optional.ofNullable(values.get(value)).orElse(없음);
  }
}