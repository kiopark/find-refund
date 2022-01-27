package szs.findrefund.web.dto.scrap;

import lombok.Getter;

import java.util.List;

@Getter
public class JsonListDto {

  private List<IncomeDetailDto> scrap001;

  private List<IncomeClassficationDto> scrap002;

  private String errMsg;

  private String company;

  private String svcCd;

  private String userId;

}