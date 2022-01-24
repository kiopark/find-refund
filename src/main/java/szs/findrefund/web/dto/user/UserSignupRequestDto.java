package szs.assignment.web.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import szs.findrefund.domain.user.User;

@Getter
@NoArgsConstructor
public class UserSignupRequestDto {

  private String userId;

  private String password;

  private String name;

  private String regNo;

  @Builder
  public UserSignupRequestDto(String userId, String password, String name, String regNo) {
    this.userId = userId;
    this.password = password;
    this.name = name;
    this.regNo = regNo;
  }

  public User toEntity() {
    return User.builder()
        .userId(userId)
        .password(password)
        .build();
  }

}
