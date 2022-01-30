package szs.findrefund.web.dto.scrap;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ScrapResponseDto {
  private JsonListDto jsonList;

  private String appVer;

  private String hostNm;

  private LocalDateTime workerResDt;

  private LocalDateTime workerReqDt;

  @Builder
  public ScrapResponseDto(JsonListDto jsonList, String appVer, String hostNm,
                          LocalDateTime workerResDt, LocalDateTime workerReqDt) {
    this.jsonList = jsonList;
    this.appVer = appVer;
    this.hostNm = hostNm;
    this.workerResDt = workerResDt;
    this.workerReqDt = workerReqDt;
  }
}