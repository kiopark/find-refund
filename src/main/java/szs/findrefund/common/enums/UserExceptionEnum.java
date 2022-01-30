package szs.findrefund.common.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@RequiredArgsConstructor
public enum UserExceptionEnum {

  VALIDATED_DUPLICATED_USERS("R001","이미 존재하는 회원 입니다."),
  USER_NOT_FOUND("R002","해당 유저의 정보를 찾을 수 없습니다."),
  PASSWORD_NOT_MATCHED("R003","비밀번호가 일치하지 않아 로그인에 실패하였습니다."),
  REGNO_NOT_MATCHED("R004","사용할 수 없는 주민등록번호 형식입니다."),
  NON_AVAILABLE_USER("R004","가입 가능한 회원이 아닙니다.");

  private final String errorCode;
  private final String msg;

}
