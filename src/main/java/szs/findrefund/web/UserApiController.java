package szs.findrefund.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import szs.findrefund.service.user.UserService;
import szs.findrefund.util.JWTUtil;
import szs.findrefund.web.dto.jwt.JwtResponseDto;
import szs.findrefund.web.dto.user.UserInfoResponseDto;
import szs.findrefund.web.dto.user.UserLoginRequestDto;
import szs.findrefund.web.dto.user.UserSignUpRequestDto;
import szs.findrefund.web.dto.user.UserSignUpResponseDto;

import javax.servlet.http.HttpServletRequest;

@Api(tags = "SignUp / LogIn")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/szs")
public class UserApiController {

  private final UserService userService;

  @ApiOperation(value = "회원가입", notes = "회원가입을 진행 합니다.")
  @PostMapping("/signup")
  public ResponseEntity<UserSignUpResponseDto> signUp(@ApiParam(value = "회원가입 정보")
                                   @RequestBody @Validated UserSignUpRequestDto requestDto) throws Exception {
    return ResponseEntity.ok().body(userService.signUp(requestDto));
  }

  @ApiOperation(value = "로그인", notes = "로그인을 진행 합니다.")
  @PostMapping("/login")
  public ResponseEntity<JwtResponseDto> login(@ApiParam(value = "로그인 정보")
                                              @RequestBody @Validated UserLoginRequestDto requestDto) throws Exception {
    String jwtToken = userService.login(requestDto);
    return ResponseEntity.ok().body(new JwtResponseDto(jwtToken));
  }

  @ApiOperation(value = "내 정보 조회", notes = "인증 토큰을 이용해 내 정보를 조회 합니다.")
  @GetMapping("/me")
  public ResponseEntity<UserInfoResponseDto> me(HttpServletRequest request) throws Exception {
    String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION);
    Long idFromToken = JWTUtil.getIdFromToken(jwtToken);
    return ResponseEntity.ok().body(userService.findMyInfo(idFromToken));
  }

}
