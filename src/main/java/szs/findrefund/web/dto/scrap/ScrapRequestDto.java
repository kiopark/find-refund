package szs.findrefund.web.dto.scrap;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScrapRequestDto {

  private String name;

  private String regNo;

  @Builder
  @JsonCreator
  public ScrapRequestDto(String name, String regNo) {
    this.name = name;
    this.regNo = regNo;
  }

}