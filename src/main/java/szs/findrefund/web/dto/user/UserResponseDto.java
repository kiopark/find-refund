package szs.findrefund.web.dto.user;

import lombok.Getter;
import szs.findrefund.domain.user.User;

@Getter
public class UserResponseDto {

  private String userId;
  private String password;
  private String name;
  private String regNo;

  public UserResponseDto(User entity) {
    this.userId = entity.getUserId();
    this.password = entity.getPassword();
    this.name = entity.getName();
    this.regNo = entity.getRegNo();
  }
}
