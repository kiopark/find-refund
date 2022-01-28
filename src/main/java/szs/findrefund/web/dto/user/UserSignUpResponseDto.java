package szs.findrefund.web.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@ApiModel(value = "회원가입 응답정보")
public class UserSignUpResponseDto {

  @ApiModelProperty(value = "응답 성공 여부")
  private boolean success;

  @ApiModelProperty(value = "응답 코드")
  private int code;

  @ApiModelProperty(value = "응답 메시지")
  private String message;

  @Builder
  public UserSignUpResponseDto(boolean success, int code, String message) {
    this.success = success;
    this.code = code;
    this.message = message;
  }

}
