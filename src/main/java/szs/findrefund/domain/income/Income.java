package szs.findrefund.domain.income;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import szs.findrefund.domain.BaseTimeEntity;
import szs.findrefund.domain.incomeClassfication.IncomeClassfication;
import szs.findrefund.domain.scrapLog.ScrapLog;
import szs.findrefund.domain.scrapStatus.ScrapStatus;
import szs.findrefund.util.AESCryptoUtil;
import szs.findrefund.web.dto.scrap.IncomeClassficationDto;
import szs.findrefund.web.dto.scrap.IncomeDetailDto;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Income extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "income_id")
  private Long id;

  private String incomeType;

  private BigDecimal totalPaymentAmount;

  private String businessStartDate;

  private String enterpriseName;

  private String userName;

  private String regNo;

  private String paymentDate;

  private String businessEndDate;

  private String incomeDivision;

  private String enterpriseRegNo;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "income_classfication_id")
  private IncomeClassfication incomeClassfication;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "scrap_status_id")
  private ScrapStatus scrapStatus;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "scrap_log_id")
  private ScrapLog scrapLog;

  @Builder
  public Income(IncomeDetailDto incomeDetailDto, IncomeClassficationDto incomeClassficationDto,
                ScrapStatus scrapStatus, ScrapLog scrapLog) throws Exception {
    this.incomeType = incomeDetailDto.getIncomeType();
    this.totalPaymentAmount = incomeDetailDto.getTotalPaymentAmount();
    this.businessStartDate = incomeDetailDto.getBusinessStartDate();
    this.enterpriseName = incomeDetailDto.getEnterpriseName();
    this.userName = incomeDetailDto.getUserName();
    this.regNo = AESCryptoUtil.encrypt(incomeDetailDto.getRegNo());
    this.paymentDate = incomeDetailDto.getPaymentDate();
    this.businessEndDate = incomeDetailDto.getBusinessEndDate();
    this.incomeDivision = incomeDetailDto.getIncomeDivision();
    this.enterpriseRegNo = incomeDetailDto.getEnterpriseRegNo();
    this.incomeClassfication = incomeClassficationDto.toEntity();
    this.scrapStatus = scrapStatus;
    this.scrapLog = scrapLog;
  }
}