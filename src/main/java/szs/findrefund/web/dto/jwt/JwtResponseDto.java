package szs.findrefund.web.dto.jwt;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@ApiModel(value = "로그인한 회원 토큰 정보")
public class JwtResponseDto {

  @ApiModelProperty("성공토큰")
  private final String accessToken;
}
