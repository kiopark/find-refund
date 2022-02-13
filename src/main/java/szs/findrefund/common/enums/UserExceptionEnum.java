package szs.findrefund.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserExceptionEnum {

  VALIDATED_DUPLICATED_USERS("U001","이미 존재하는 회원 입니다."),
  VALIDATED_DUPLICATED_ID("U002","이미 존재하는 아이디 입니다."),
  USER_NOT_FOUND("U003","해당 유저의 정보를 찾을 수 없습니다."),
  PASSWORD_NOT_MATCHED("U004","비밀번호가 일치하지 않아 로그인에 실패하였습니다."),
  REGNO_NOT_MATCHED("U005","사용할 수 없는 주민등록번호 형식입니다."),
  NON_AVAILABLE_USER("U006","가입 가능한 회원이 아닙니다.");

  private final String errorCode;
  private final String msg;

}
