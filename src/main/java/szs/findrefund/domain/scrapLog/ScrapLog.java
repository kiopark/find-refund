package szs.findrefund.domain.scrapLog;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import szs.findrefund.domain.BaseEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ScrapLog extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "scrap_log_id")
  private Long id;

  private String appVer;

  private String hostNm;

  private LocalDateTime workerResDt;

  private LocalDateTime workerReqDt;

  @Builder
  public ScrapLog(String appVer, String hostNm, LocalDateTime workerResDt, LocalDateTime workerReqDt) {
    this.appVer = appVer;
    this.hostNm = hostNm;
    this.workerResDt = workerResDt;
    this.workerReqDt = workerReqDt;
  }
}
