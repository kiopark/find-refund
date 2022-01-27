package szs.findrefund.web.dto.scrap;

import lombok.Getter;

@Getter
public class ScrapResponseDto {
  private JsonListDto jsonList;

  private String appVer;

  private String hostNm;

  private String workerResDt;

  private String workerReqDt;
}