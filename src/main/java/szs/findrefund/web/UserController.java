package szs.findrefund.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import szs.assignment.web.dto.user.UserSignupRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import szs.findrefund.service.user.UserService;
import szs.findrefund.web.dto.user.UserLoginRequestDto;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/szs")
public class UserController {

  private final UserService userService;

  @PostMapping("/signup")
  public ResponseEntity<Object> signup(@RequestBody @Validated UserSignupRequestDto requestDto) throws Exception {
    return ResponseEntity.ok("hi");
  }

  @PostMapping("/login")
  public ResponseEntity<Object> login(@RequestBody @Validated UserLoginRequestDto requestDto) throws Exception {
    return ResponseEntity.ok("hi");
  }

  @GetMapping("/me")
  public ResponseEntity<Object> userInfo(HttpServletRequest request) {
    return ResponseEntity.ok("hi");
  }


}
