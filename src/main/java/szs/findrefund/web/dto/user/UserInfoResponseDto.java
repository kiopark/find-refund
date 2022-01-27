package szs.findrefund.web.dto.user;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserInfoResponseDto {

  private final String userId;
  private final String name;
  private final String regNo;

  @Builder
  public UserInfoResponseDto(String userId, String name, String regNo) {
    this.userId = userId;
    this.name = name;
    this.regNo = regNo;
  }
}
