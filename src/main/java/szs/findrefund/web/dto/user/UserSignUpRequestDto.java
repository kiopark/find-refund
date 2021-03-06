package szs.findrefund.web.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import szs.findrefund.domain.user.User;

import javax.validation.constraints.NotBlank;

@ApiModel(value = "회원가입 요청정보")
@Getter
@NoArgsConstructor
public class UserSignUpRequestDto {

  @ApiModelProperty(value = "아이디")
  @NotBlank(message = "아이디를 입력해주세요.")
  private String userId;

  @ApiModelProperty(value = "비밀번호")
  @NotBlank(message = "비밀번호를 입력해주세요.")
  private String password;

  @ApiModelProperty(value = "이름")
  @NotBlank(message = "이름을 입력해주세요.")
  private String name;

  @ApiModelProperty(value = "주민등록번호")
  @NotBlank(message = "주민등록번호를 입력해주세요.")
  private String regNo;

  @Builder
  public UserSignUpRequestDto(String userId, String password, String name, String regNo) {
    this.userId = userId;
    this.password = password;
    this.name = name;
    this.regNo = regNo;
  }

  public void encryptTheRegNo(String regNo) {
    this.regNo = regNo;
  }

  public void encryptThePassword(String encodePassword) {
    this.password = encodePassword;
  }

  public User toEntity() {
    return User.builder()
        .name(name)
        .userId(userId)
        .password(password)
        .regNo(regNo)
        .build();
  }

}
