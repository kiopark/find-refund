package szs.findrefund.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import szs.findrefund.web.dto.user.UserSignUpRequestDto;

import java.util.*;

@Getter
@RequiredArgsConstructor
public enum AvailableUserEnum {

  홍길동("홍길동", "860824-1655068"),
  김둘리("김둘리", "921108-1582816"),
  마징가("마징가", "880601-2455116"),
  베지터("베지터", "910411-1656116"),
  손오공("손오공", "820326-2715702"),

  없음(null, null);

  private final String name;
  private final String regNo;

  public static Optional<AvailableUserEnum> find(UserSignUpRequestDto requestDto) {
    return Arrays.stream(values())
                 .filter(item -> requestDto.getName().equals(item.name) &&
                                 requestDto.getRegNo().equals(item.regNo))
                 .findFirst();
  }

}
