package szs.findrefund.api;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommonResult {

  @ApiModelProperty(value = "응답 성공 여부")
  private boolean success;

  @ApiModelProperty(value = "응답 코드")
  private int code;

  @ApiModelProperty(value = "응답 메시지")
  private String message;

  public void matchCommonResult(boolean success, int code, String message) {
    this.success = success;
    this.code = code;
    this.message = message;
  }
}
