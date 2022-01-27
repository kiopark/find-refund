package szs.findrefund.web.dto.scrap;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class IncomeClassficationDto {

  /**
   * 소득구분
   */
  private BigDecimal classfication;

  /**
   * 사용금액
   */
  private BigDecimal usedAmount;
}