package szs.findrefund.web.dto.refund;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@ApiModel(value = "환급액 조회 결과")
public class RefundResponseDto {

  @JsonProperty(value = "이름")
  private final String name;

  @JsonProperty(value = "한도")
  private final BigDecimal limitAmount;

  @JsonProperty(value = "공제액")
  private final BigDecimal deductionAmount;

  @JsonProperty(value = "환급액")
  private final BigDecimal refundAmount;

  @Builder
  public RefundResponseDto(String name, BigDecimal limitAmount, BigDecimal deductionAmount, BigDecimal refundAmount) {
    this.name = name;
    this.limitAmount = limitAmount;
    this.deductionAmount = deductionAmount;
    this.refundAmount = refundAmount;
  }
}
