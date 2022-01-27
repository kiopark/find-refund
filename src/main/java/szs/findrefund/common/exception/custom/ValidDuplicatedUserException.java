package szs.findrefund.common.exception.custom;

import szs.findrefund.common.enums.UserExceptionEnum;

public class ValidDuplicatedUserException extends RuntimeException {

  public ValidDuplicatedUserException(UserExceptionEnum exceptionEnum) {
    super(exceptionEnum.getMsg());
  }
}
