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
    if (result.equals(0)) {
      return setFailResult();
    } else {
      return setSuccessResult();
    }
  }

  private static UserSignUpResponseDto setSuccessResult() {
    return UserSignUpResponseDto.builder()
                                .success(true)
                                .code(SignUpEnum.SUCCESS.getStatus())
                                .message(SignUpEnum.SUCCESS.getMessage())
                                .build();
  }

  private static UserSignUpResponseDto setFailResult() {
    return UserSignUpResponseDto.builder()
                                .success(false)
                                .code(SignUpEnum.FAIL.getStatus())
                                .message(SignUpEnum.FAIL.getMessage())
                                .build();
  }

}
