package szs.findrefund.web.dto.scrap;

import lombok.Getter;

@Getter
public class IncomeDetailDto {

  /**
   * 소득내역
   */
  private String incomeHistory;

  /**
   * 총지급액
   */
  private String totalPaymentAmount;

  /**
   * 업무시작일
   */
  private String businessStartDate;

  /**
   * 기업명
   */
  private String enterpriseName;

  /**
   * 이름
   */
  private String userName;

  /**
   * 주민등록번호
   */
  private String regNo;

  /**
   * 지급일
   */
  private String paymentDate;

  /**
   * 업무종료일
   */
  private String businessEndDate;

  /**
   * 소득구분
   */
  private String type;

  /**
   * 사업자등록번호
   */
  private String enterpriseRegNo;

}