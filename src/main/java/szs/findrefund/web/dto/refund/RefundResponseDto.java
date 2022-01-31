package szs.findrefund.web.dto.refund;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

import static szs.findrefund.common.formatter.RefundFormatter.refundFormatter;

@Getter
@ApiModel(value = "환급액 조회 결과")
public class RefundResponseDto {

  @JsonProperty(value = "이름")
  private final String name;

  @JsonProperty(value = "한도")
  private final String limitAmount;

  @JsonProperty(value = "공제액")
  private final String deductionAmount;

  @JsonProperty(value = "환급액")
  private final String refundAmount;

  @Builder
  public RefundResponseDto(String name, BigDecimal limitAmount, BigDecimal deductionAmount, BigDecimal refundAmount) {
    this.name = name;
    this.limitAmount = refundFormatter(limitAmount);
    this.deductionAmount = refundFormatter(deductionAmount);
    this.refundAmount = refundFormatter(refundAmount);
  }
}
