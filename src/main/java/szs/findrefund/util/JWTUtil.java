package szs.findrefund.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import szs.findrefund.domain.user.User;

import javax.annotation.PostConstruct;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static szs.findrefund.common.Constants.JwtConst.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class JWTUtil {

  @Value("${jwt.secret}")
  private String temp_secretKey;

  private static String secretKey;

  @Value("${jwt.token-expire-seconds}")
  private long temp_expire_seconds;

  private static long expire_seconds;

  @PostConstruct
  protected void init() {
    secretKey = Base64.getEncoder().encodeToString(temp_secretKey.getBytes());
    expire_seconds = temp_expire_seconds;
  }

  /**
   * JWT 생성
   */
  public static String createJwt(User user) {
    Date currentDate = new Date();
    return Jwts.builder()
               .setHeader(createHeader())
               .setClaims(createClaims(user))
               .setIssuedAt(currentDate)
               .setExpiration(new Date(currentDate.getTime() + expire_seconds))
               .signWith(createSigningKey(), SignatureAlgorithm.HS256)
               .compact();
  }

  /**
   * JWT 헤더 생성
   */
  private static Map<String, Object> createHeader() {
    Map<String, Object> header = new HashMap<>();
    header.put("typ", "JWT");
    header.put("alg", "HS256");
    header.put("regDate", System.currentTimeMillis());
    return header;
  }

  /**
   * JWT 클레임 생성 - 구분이 가능한 PK 값 담기
   */
  private static Map<String, Object> createClaims(User user) {
    Map<String, Object> claims = new HashMap<>();
    claims.put(PK_ID, String.valueOf(user.getId()));
    return claims;
  }

  /**
   * JWT 키 값 생성
   */
  private static Key createSigningKey() {
    return new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS256.getJcaName());
  }

  /**
   * JWT 에서 클레임 내용 조회
   */
  public static Claims getClaimsFromToken(String jwt) {
    return Jwts.parserBuilder()
               .setSigningKey(secretKey.getBytes())
               .build()
               .parseClaimsJws(jwt)
               .getBody();
  }

  /**
   * JWT 클레임 에서 ID 조회
   */
  public static Long getIdFromToken(String jwt) {
    Claims claimsFromToken = getClaimsFromToken(jwt);
    return Long.parseLong(String.valueOf(claimsFromToken.get(PK_ID)));
  }

  /**
   * HttpServletRequest Header 의 JWT 정보
   */
  public static String resolveToken(HttpServletRequest request) {
    return request.getHeader(HttpHeaders.AUTHORIZATION);
  }

  /**
   * JWT 만료일자 및 유효성 검사
   */
  public static boolean isValidToken(String token) {
    Claims claims = getClaimsFromToken(token);
    return !claims.getExpiration().before(new Date());
  }

}
