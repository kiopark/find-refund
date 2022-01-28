package szs.findrefund.interceptor;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import szs.findrefund.common.enums.JwtExceptionEnum;
import szs.findrefund.common.exception.jwt.custom.JwtValidException;
import szs.findrefund.util.JWTUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationInterceptor implements HandlerInterceptor {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    String jwtToken = JWTUtil.resolveToken(request);

    if (!ObjectUtils.isEmpty(jwtToken)) {
      try {
        if (JWTUtil.isValidToken(jwtToken)) {
          return true;
        }
      } catch (ExpiredJwtException e) {
        throw new JwtValidException(JwtExceptionEnum.TOKEN_EXPIRED);
      } catch (JwtException e) {
        throw new JwtValidException(JwtExceptionEnum.TOKEN_TAMPERED);
      }
    }
    throw new JwtValidException(JwtExceptionEnum.TOKEN_IS_NULL);
  }
}
