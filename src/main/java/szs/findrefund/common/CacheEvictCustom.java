package szs.findrefund.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CacheEvictCustom {

  /**
   * 환급액 조회 캐시 초기화
   */
  @CacheEvict(value = "accessToken", key = "#accessToken")
  public void clearCache(String accessToken){
    log.debug("환급액 조회 캐시 초기화");
  }

}
