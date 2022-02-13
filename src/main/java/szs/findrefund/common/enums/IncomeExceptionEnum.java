package szs.findrefund.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum IncomeExceptionEnum {

  SCRAP_LOADING_EXCEPTION("I001","정보를 불러오는 중 입니다. 잠시만 기다려주세요.");

  private final String errorCode;
  private final String msg;

}
