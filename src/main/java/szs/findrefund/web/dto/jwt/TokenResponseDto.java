package szs.findrefund.web.dto.jwt;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TokenResponseDto {
  private final String accessToken;
}
