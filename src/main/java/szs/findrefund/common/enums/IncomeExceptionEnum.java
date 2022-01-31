package szs.findrefund.common.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@RequiredArgsConstructor
public enum IncomeExceptionEnum {

  SCRAP_LOADING_EXCEPTION("I001","정보를 불러오는 중 입니다. 잠시만 기다려주세요.");

  private final String errorCode;
  private final String msg;

}
