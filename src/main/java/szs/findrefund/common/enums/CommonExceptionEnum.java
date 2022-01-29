package szs.findrefund.common.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@RequiredArgsConstructor
public enum CommonExceptionEnum {

  INVALID_INPUT_VALUE("C001"," 잘못된 입력 정보 입니다."),
  METHOD_NOT_ALLOWED("C002"," 지원하지 않는 HTTP Method 입니다.");

  private final String errorCode;
  private final String msg;

}
