package szs.findrefund.web.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import szs.findrefund.common.enums.SignUpEnum;

@Getter
@NoArgsConstructor
@ApiModel(value = "회원가입 응답정보")
public class UserSignUpResponseDto {

  @ApiModelProperty(value = "응답 코드")
  private SignUpEnum responseCode;

  @Builder
  public UserSignUpResponseDto(SignUpEnum responseCode) {
    this.responseCode = responseCode;
  }

}
