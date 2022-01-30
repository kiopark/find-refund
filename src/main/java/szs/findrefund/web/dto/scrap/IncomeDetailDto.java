package szs.findrefund.web.dto.scrap;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class IncomeDetailDto {

  @JsonProperty("소득내역")
  private String incomeType;

  @JsonProperty("총지급액")
  private BigDecimal totalPaymentAmount;

  @JsonProperty("업무시작일")
  private String businessStartDate;

  @JsonProperty("기업명")
  private String enterpriseName;

  @JsonProperty("이름")
  private String userName;

  @JsonProperty("주민등록번호")
  private String regNo;

  @JsonProperty("지급일")
  private String paymentDate;

  @JsonProperty("업무종료일")
  private String businessEndDate;

  @JsonProperty("소득구분")
  private String incomeDivision;

  @JsonProperty("사업자등록번호")
  private String enterpriseRegNo;

  @Builder
  public IncomeDetailDto(String incomeType, BigDecimal totalPaymentAmount, String businessStartDate,
                         String enterpriseName, String userName, String regNo, String paymentDate,
                         String businessEndDate, String incomeDivision, String enterpriseRegNo) {
    this.incomeType = incomeType;
    this.totalPaymentAmount = totalPaymentAmount;
    this.businessStartDate = businessStartDate;
    this.enterpriseName = enterpriseName;
    this.userName = userName;
    this.regNo = regNo;
    this.paymentDate = paymentDate;
    this.businessEndDate = businessEndDate;
    this.incomeDivision = incomeDivision;
    this.enterpriseRegNo = enterpriseRegNo;
  }
}