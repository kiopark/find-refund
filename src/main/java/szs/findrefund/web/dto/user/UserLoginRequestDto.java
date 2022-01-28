package szs.findrefund.web.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@ApiModel(value = "로그인")
public class UserLoginRequestDto {

  @ApiModelProperty("아이디")
  @NotBlank(message = "아이디를 입력해주세요.")
  private String userId;

  @ApiModelProperty("비밀번호")
  @NotBlank(message = "비밀번호를 입력해주세요.")
  private String password;

  @Builder
  public UserLoginRequestDto(String userId, String password) {
    this.userId = userId;
    this.password = password;
  }

}
