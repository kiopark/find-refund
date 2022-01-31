package szs.findrefund.interceptor;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import szs.findrefund.common.exception.jwt.custom.JwtExpiredException;
import szs.findrefund.common.exception.jwt.custom.JwtTemperedException;
import szs.findrefund.common.exception.jwt.custom.JwtValidException;
import szs.findrefund.util.JWTUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationInterceptor implements HandlerInterceptor {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
    String jwtToken = JWTUtil.resolveToken(request);

    if (!ObjectUtils.isEmpty(jwtToken)) {
      try {
        if (JWTUtil.isValidToken(jwtToken)) {
          return true;
        }
      } catch (ExpiredJwtException e) {
        throw new JwtExpiredException();
      } catch (JwtException e) {
        throw new JwtTemperedException();
      }
    }
    throw new JwtValidException();
  }
}
