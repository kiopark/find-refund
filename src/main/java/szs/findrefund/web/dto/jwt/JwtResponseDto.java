package szs.findrefund.web.dto.jwt;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class JwtResponseDto {

  private final String accessToken;
}
