package szs.findrefund.web.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@ApiModel(value = "내 정보")
public class UserInfoResponseDto {

  @ApiModelProperty("아이디")
  private final String userId;

  @ApiModelProperty("이름")
  private final String name;

  @ApiModelProperty("주민등록번호")
  private final String regNo;

  @Builder
  public UserInfoResponseDto(String userId, String name, String regNo) {
    this.userId = userId;
    this.name = name;
    this.regNo = regNo;
  }
}
