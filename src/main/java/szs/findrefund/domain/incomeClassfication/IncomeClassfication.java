package szs.findrefund.domain.incomeClassfication;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import szs.findrefund.domain.BaseEntity;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class IncomeClassfication extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "income_classfication_id")
  private Long id;

  private String classfication;

  private BigDecimal usedTotalAmount;

  @Builder
  public IncomeClassfication(String classfication, BigDecimal usedTotalAmount) {
    this.classfication = classfication;
    this.usedTotalAmount = usedTotalAmount;
  }
}