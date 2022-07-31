package szs.findrefund.common.formatter;

import java.math.BigDecimal;

public class RefundFormatter {

  public static String refundFormatter(BigDecimal refund) {

    String[] unit = {"", "만", "억", "조", "경"};
    refund = refund.setScale(0, BigDecimal.ROUND_FLOOR);
    String refundKor = refund.toString();
    int length = refundKor.length() - 1;

    StringBuilder result = new StringBuilder();
    for (int i = length; i >= 3; i--) {
      char zeroInt = Integer.toString(0).charAt(0);
      char initInt = refundKor.charAt(length - i);

      if (i > 3) {
        result.append(initInt);
      }

      if (initInt != zeroInt && i == 3) {
        result.append(initInt).append("천");
      }

      if (i % 4 == 0) {
        result.append(unit[i / 4]);
      }
    }

    result.append("원");
    return result.toString();
  }
}
