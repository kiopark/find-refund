package szs.findrefund.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Base64;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {

  /**
   * 근로소득 세액공제 상수 Class
   */
  public static final class RefundConst {
    /* 3,300만원 이하 총 지급액 */
    public static final BigDecimal MIN_PAYMENT_AMOUNT = BigDecimal.valueOf(33000000);
    /* 7,000만원 초과 총 지급액 */
    public static final BigDecimal MAX_PAYMENT_AMOUNT = BigDecimal.valueOf(70000000);
    /* 공제 요건 최고 기준 */
    public static final BigDecimal MAX_REFUND_AMOUNT = BigDecimal.valueOf(740000);
    /* 공제 요건 중간 기준 */
    public static final BigDecimal MIDDLE_REFUND_AMOUNT = BigDecimal.valueOf(660000);
    /* 공제 요건 최저 기준 */
    public static final BigDecimal MIN_REFUND_AMOUNT = BigDecimal.valueOf(500000);
    /* 산출세액 기준 */
    public static final BigDecimal TAX_AMOUNT = BigDecimal.valueOf(1300000);
    /* 공재액 */
    public static final BigDecimal DEDUCTION_AMOUNT = BigDecimal.valueOf(715000);
  }

  /**
   * URL 관련 상수 Class
   */
  public static final class UrlConst {
    /* ULR 스크랩 API 주소 */
    public static final String SCRAP_URL = "https://codetest.3o3.co.kr/scrap/";
  }

  /**
   * Pattern 관련 상수 Class
   */
  public static final class PatternConst {
    /* 주민등록번호 정규식 패턴 */
    public static final String REGIST_REG_NO_RULE = "\\d{6}\\-[1-4]\\d{6}";
    /* JWT Interceptor 제외할 경로 */
    public static final String[] EXCLUDE_PATTERNS = {
        "/", "/error", "/csrf", "/favicon.ico", "/swagger-ui.html", "/swagger-resources/**",
        "/v2/api-docs", "/webjars/**", "/*/*/login", "/*/*/signup", "/h2-console/**"};
  }

  /**
   * JWT 관련 상수 Class
   */
  public static final class JwtConst {
    /* Claim 에 저장된 키 값 */
    public static final String PK_ID = "id";
    /* 테스트 전용 secret key */
    public static final String FAKE_SECRET_KEY =
        Base64.getEncoder().encodeToString("test12345678909876543212345".getBytes());
    /* 테스트 전용 발급 토큰 */
    public static final String FAKE_ACCESS_TOKEN =
        "eyJyZWdEYXRlIjoxNjQzNjA3MTc3Mzk4LCJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9" +
        ".eyJpZCI6IjEiLCJpYXQiOjE2NDM2MDcxNzcsImV4cCI6MTY0NDQ3MTE3N30" +
        ".dVkoEqVFjgFwD0iXRj_DfiGgw59Ci0VBtyg7qzEG618";
  }

  /**
   * 암/복호화 관련 상수 Class
   */
  public static final class AESCryptoConst {
    /* 암호화 패딩 값 */
    public static final String SPEC_NAME = "AES/CBC/PKCS5Padding";
    /* 암호화 키 */
    public static final byte[] SPEC_KEY = "aeskey12345678987654321234567898".getBytes();
    /* 블럭 암호화 순서 및 규칙 (초기화 벡터) */
    public static final byte[] IV = { 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08,
                                      0x09, 0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16 };
  }
}