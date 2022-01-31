package szs.findrefund.domain.scrapStatus;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import szs.findrefund.domain.BaseEntity;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ScrapStatus extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "scrap_status_id")
  private Long id;

  private String errMsg;

  private String company;

  private String svcCd;

  private String userId;

  @Builder
  public ScrapStatus(String errMsg, String company, String svcCd, String userId) {
    this.errMsg = errMsg;
    this.company = company;
    this.svcCd = svcCd;
    this.userId = userId;
  }
}
