package szs.findrefund.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@AllArgsConstructor
public enum ResponseCode {

  SUCCESS(1, "회원가입에 성공하였습니다."),
  FAIL(0, "회원가입에 실패하였습니다.");

  private int status;
  private String message;

}
