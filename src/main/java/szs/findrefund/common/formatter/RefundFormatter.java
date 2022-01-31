package szs.findrefund.common.formatter;

import java.math.BigDecimal;

public class RefundFormatter {

  public static String refundFormatter(BigDecimal refund) {

    String[] unit = {"", "만", "억", "조", "경"};
    refund = refund.setScale(0, BigDecimal.ROUND_FLOOR);
    String refundKor = refund.toString();
    int length = refundKor.length() - 1;

    String result = "";
    for (int i = length; i >= 3; i--) {
      char zeroInt = Integer.toString(0).charAt(0);
      char initInt = refundKor.charAt(length - i);

      if (i > 3) {
        result += initInt;
      }

      if (initInt != zeroInt && i == 3) {
        result += (initInt + "천");
      }

      if (i % 4 == 0) {
        result += unit[i / 4];
      }
    }

    result += "원";
    return result;
  }
}
