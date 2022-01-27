package szs.findrefund.web.dto.scrap;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScrapRequestDto {

  @JsonProperty("name")
  private String name;

  @JsonProperty("regNo")
  private String regNo;

  @Builder
  @JsonCreator
  public ScrapRequestDto(String name, String regNo) {
    this.name = name;
    this.regNo = regNo;
  }

}