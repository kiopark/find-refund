package szs.findrefund.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import szs.findrefund.interceptor.JwtAuthenticationInterceptor;

import static szs.findrefund.common.Constants.*;

@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {

  private final JwtAuthenticationInterceptor jwtAuthenticationInterceptor;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(jwtAuthenticationInterceptor)
            .addPathPatterns("/**")
            .excludePathPatterns(PatternConst.EXCLUDE_PATTERNS);
  }

}
