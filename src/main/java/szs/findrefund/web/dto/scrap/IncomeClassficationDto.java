package szs.findrefund.web.dto.scrap;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import szs.findrefund.domain.incomeClassfication.IncomeClassfication;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class IncomeClassficationDto {

  @JsonProperty(value = "소득구분")
  private String classfication;

  @JsonProperty(value = "총사용금액")
  private BigDecimal usedTotalAmount;

  public IncomeClassfication toEntity() {
    return IncomeClassfication.builder()
        .classfication(classfication)
        .usedTotalAmount(usedTotalAmount)
        .build();
  }

  @Builder
  public IncomeClassficationDto(String classfication, BigDecimal usedTotalAmount) {
    this.classfication = classfication;
    this.usedTotalAmount = usedTotalAmount;
  }
}