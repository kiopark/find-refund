package szs.findrefund.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum  JwtExceptionEnum {

  TOKEN_EXPIRED("J001","만료기간이 지난 토큰 입니다."),
  TOKEN_TAMPERED("J002","변조된 토큰 입니다."),
  TOKEN_IS_NULL("J003","존재하지 않는 토큰 입니다.");

  private final String errorCode;
  private final String msg;


}
