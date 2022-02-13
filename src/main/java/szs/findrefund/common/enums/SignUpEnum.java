package szs.findrefund.common.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import szs.findrefund.web.dto.user.UserSignUpResponseDto;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@RequiredArgsConstructor
public enum SignUpEnum {

  SUCCESS(1, "회원가입에 성공하였습니다."),
  FAIL(0, "회원가입에 실패하였습니다.");

  private final int status;
  private final String message;

  public static UserSignUpResponseDto signUpResult(Long result) {
    SignUpEnum signUpEnum = result > 0 ? SUCCESS : FAIL;
    return UserSignUpResponseDto.builder()
                                .responseCode(signUpEnum)
                                .build();
  }

}
