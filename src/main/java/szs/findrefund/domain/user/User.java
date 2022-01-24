package szs.findrefund.domain.user;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import szs.findrefund.domain.BaseTimeEntity;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User extends BaseTimeEntity {

  @Id @GeneratedValue
  @Column(updatable = false)
  private Long id;

  @Column(nullable = false)
  private String userId;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String regNo;

  @Builder
  public User(String userId, String password, String name, String regNo) {
    this.userId = userId;
    this.password = password;
    this.name = name;
    this.regNo = regNo;
  }
}
