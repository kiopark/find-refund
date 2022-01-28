package szs.findrefund.web.dto.scrap;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ScrapResponseDto {
  private JsonListDto jsonList;

  private String appVer;

  private String hostNm;

  private LocalDateTime workerResDt;

  private LocalDateTime workerReqDt;
}