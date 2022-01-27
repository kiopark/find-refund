package szs.findrefund.domain.refund;

import lombok.Getter;
import lombok.NoArgsConstructor;
import szs.findrefund.domain.BaseTimeEntity;
import szs.findrefund.domain.user.User;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@Entity
public class Refund extends BaseTimeEntity {

  @Id @GeneratedValue
  @Column(name = "refundId")
  private Long id;

  private BigDecimal totalPaymentAmount;

  private String errMsg;

  private String company;

  private String svcCd;

  private String appVer;

  private String hostNm;

  private String workerResDt;

  private String workerReqDt;

}