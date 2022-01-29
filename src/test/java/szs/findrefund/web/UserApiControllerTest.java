package szs.findrefund.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import szs.findrefund.common.enums.CommonExceptionEnum;
import szs.findrefund.service.user.UserService;
import szs.findrefund.web.dto.jwt.JwtResponseDto;
import szs.findrefund.web.dto.user.UserLoginRequestDto;
import szs.findrefund.web.dto.user.UserSignUpRequestDto;
import szs.findrefund.web.dto.user.UserSignUpResponseDto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserApiController.class)
class UserApiControllerTest {

  @MockBean
  private UserService userService;

  @Autowired
  private MockMvc mvc;

  @Autowired
  private ObjectMapper objectMapper;

  @DisplayName("[userId 미입력] 회원가입 실패")
  @Test
  void SignUp_Fail_UserId_Blank() throws Exception {
    // given
    final UserSignUpRequestDto requestDto = signUpDto("userId");
    when(userService.signUp(requestDto)).thenReturn(new UserSignUpResponseDto());

    // when
    ResultActions resultActions = requestPostSignUp(requestDto);

    // then
    resultActions
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("msg").value(CommonExceptionEnum.INVALID_INPUT_VALUE.getMsg()));
  }

  @DisplayName("[password 미입력] 회원가입 실패")
  @Test
  void SignUp_Fail_Password_Blank() throws Exception {
    // given
    final UserSignUpRequestDto requestDto = signUpDto("password");
    when(userService.signUp(requestDto)).thenReturn(new UserSignUpResponseDto());

    // when
    ResultActions resultActions = requestPostSignUp(requestDto);

    // then
    resultActions
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("msg").value(CommonExceptionEnum.INVALID_INPUT_VALUE.getMsg()));
  }

  @DisplayName("[name 미입력] 회원가입 실패")
  @Test
  void SignUp_Fail_Name_Blank() throws Exception {
    // given
    final UserSignUpRequestDto requestDto = signUpDto("name");
    when(userService.signUp(requestDto)).thenReturn(new UserSignUpResponseDto());

    // when
    ResultActions resultActions = requestPostSignUp(requestDto);

    // then
    resultActions
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("msg").value(CommonExceptionEnum.INVALID_INPUT_VALUE.getMsg()));
  }

  @DisplayName("[regNo 미입력] 회원가입 실패")
  @Test
  void SignUp_Fail_RegNo_Blank() throws Exception {
    // given
    final UserSignUpRequestDto requestDto = signUpDto("regNo");
    when(userService.signUp(requestDto)).thenReturn(new UserSignUpResponseDto());

    // when
    ResultActions resultActions = requestPostSignUp(requestDto);

    // then
    resultActions
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("msg").value(CommonExceptionEnum.INVALID_INPUT_VALUE.getMsg()));
  }

  @DisplayName("잘못된 HTTP Method 요청")
  @Test
  void SignUp_Not_Allowed_Method() throws Exception {
    // given
    final UserSignUpRequestDto requestDto = signUpDto("");
    when(userService.signUp(requestDto)).thenReturn(new UserSignUpResponseDto());

    // when
    ResultActions resultActions = requestGetSignUp(requestDto);

    // then
    resultActions
        .andExpect(status().isMethodNotAllowed())
        .andExpect(jsonPath("msg").value(CommonExceptionEnum.METHOD_NOT_ALLOWED.getMsg()));
  }

  @DisplayName("회원가입 성공")
  @Test
  void SignUp_Success() throws Exception {
    // given
    final UserSignUpRequestDto requestDto = signUpDto("");
    when(userService.signUp(requestDto)).thenReturn(new UserSignUpResponseDto());

    // when
    ResultActions resultActions = requestPostSignUp(requestDto);

    // then
    resultActions.andExpect(status().isOk());
  }

  @DisplayName("[userId 미입력] 로그인 실패")
  @Test
  void Login_Fail_UserId_Blank() throws Exception {
    // given
    final UserLoginRequestDto requestDto = loginDto();
    when(userService.login(requestDto)).thenReturn(String.valueOf(new JwtResponseDto(null)));

    // when
    ResultActions resultActions = requestPostLogin(requestDto);

    // then
    resultActions.andExpect(status().isOk())
                 .andExpect(jsonPath("$.accessToken").doesNotExist());
  }

  @DisplayName("[passWord 미입력] 로그인 실패")
  @Test
  void Login_Fail_Password_Blank() throws Exception {
    // given
    final UserLoginRequestDto requestDto = loginDto();
    when(userService.login(requestDto)).thenReturn(String.valueOf(new JwtResponseDto(null)));

    // when
    ResultActions resultActions = requestPostLogin(requestDto);

    // then
    resultActions.andExpect(status().isOk())
                 .andExpect(jsonPath("$.accessToken").doesNotExist());
  }

  @DisplayName("로그인 성공")
  @Test
  void Login_Success() throws Exception {
    // given
    final UserLoginRequestDto requestDto = loginDto();
    when(userService.login(requestDto)).thenReturn(String.valueOf(new JwtResponseDto(null)));

    // when
    ResultActions resultActions = requestPostLogin(requestDto);

    // then
    MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
    String accessToken = mvcResult.getResponse().getContentAsString();
    assertThat(accessToken).isNotEmpty();
  }

  @DisplayName("내 정보 조회 성공")
  @Test
  void Find_My_Info_Success() throws Exception {
    // given
    final UserLoginRequestDto requestDto = loginDto();
    when(userService.login(requestDto)).thenReturn(String.valueOf(new JwtResponseDto(null)));

    // when
    ResultActions resultActions = requestPostLogin(requestDto);

    // then
    resultActions.andExpect(status().isOk())
        .andExpect(jsonPath("$.accessToken").doesNotExist());
  }

  @DisplayName("내 정보 조회 실패")
  @Test
  void Find_My_Info_Fail() throws Exception {
    // given
    final UserLoginRequestDto requestDto = loginDto();
    when(userService.login(requestDto)).thenReturn(String.valueOf(new JwtResponseDto(null)));

    // when
    ResultActions resultActions = requestPostLogin(requestDto);

    // then
    resultActions.andExpect(status().isOk())
        .andExpect(jsonPath("$.accessToken").doesNotExist());
  }

  @DisplayName("회원 회원가입 객체 생성")
  private UserSignUpRequestDto signUpDto(String type) {
    String userId = "userId";
    String password = "password";
    String name = "홍길동";
    String regNo = "860824-1655068";
    String wrongRegNo = "6824-1655068";

    switch (type) {
      case "userId": userId = "";
        break;
      case "password": password = "";
        break;
      case "name": name = "";
        break;
      case "regNo": regNo = "";
        break;
      case "wrongRegNo": regNo = wrongRegNo;
        break;
    }
    return UserSignUpRequestDto.builder()
                               .userId(userId)
                               .password(password)
                               .name(name)
                               .regNo(regNo)
                               .build();
  }

  @DisplayName("회원 로그인 객체 생성")
  private UserLoginRequestDto loginDto() {
    return UserLoginRequestDto.builder()
                              .userId("userId")
                              .password("password")
                              .build();
  }

  @DisplayName("[POST] 회원가입")
  private ResultActions requestPostSignUp(UserSignUpRequestDto requestDto) throws Exception {
    return mvc.perform(post("/api/szs/signup")
              .content(objectMapper.writeValueAsString(requestDto))
              .contentType(MediaType.APPLICATION_JSON));
  }

  @DisplayName("[GET] 회원가입")
  private ResultActions requestGetSignUp(UserSignUpRequestDto requestDto) throws Exception {
    return mvc.perform(get("/api/szs/signup")
        .content(objectMapper.writeValueAsString(requestDto))
        .contentType(MediaType.APPLICATION_JSON));
  }

  @DisplayName("[POST] 로그인")
  private ResultActions requestPostLogin(UserLoginRequestDto requestDto) throws Exception {
    return mvc.perform(post("/api/szs/login")
              .content(objectMapper.writeValueAsString(requestDto))
              .contentType(MediaType.APPLICATION_JSON));
  }

}