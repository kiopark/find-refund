package szs.findrefund.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import szs.findrefund.service.user.UserService;
import szs.findrefund.web.dto.jwt.JwtResponseDto;
import szs.findrefund.web.dto.scrap.ScrapRequestDto;
import szs.findrefund.web.dto.user.UserLoginRequestDto;
import szs.findrefund.web.dto.user.UserSignUpRequestDto;
import szs.findrefund.web.dto.user.UserSignUpResponseDto;

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
    ResultActions resultActions = requestUserSaveDto(requestDto);

    // then
    resultActions.andExpect(status().isOk());
  }

  @DisplayName("[password 미입력] 회원가입 실패")
  @Test
  void SignUp_Fail_Password_Blank() throws Exception {
    // given
    final UserSignUpRequestDto requestDto = signUpDto("password");
    when(userService.signUp(requestDto)).thenReturn(new UserSignUpResponseDto());

    // when
    ResultActions resultActions = requestUserSaveDto(requestDto);

    // then
    resultActions.andExpect(status().isOk());
  }

  @DisplayName("[name 미입력] 회원가입 실패")
  @Test
  void SignUp_Fail_Name_Blank() throws Exception {
    // given
    final UserSignUpRequestDto requestDto = signUpDto("name");
    when(userService.signUp(requestDto)).thenReturn(new UserSignUpResponseDto());

    // when
    ResultActions resultActions = requestUserSaveDto(requestDto);

    // then
    resultActions.andExpect(status().isOk());
  }

  @DisplayName("[regNo 미입력] 회원가입 실패")
  @Test
  void SignUp_Fail_RegNo_Blank() throws Exception {
    // given
    final UserSignUpRequestDto requestDto = signUpDto("regNo");
    when(userService.signUp(requestDto)).thenReturn(new UserSignUpResponseDto());

    // when
    ResultActions resultActions = requestUserSaveDto(requestDto);

    // then
    resultActions.andExpect(status().isOk());
  }

  @DisplayName("[아이디 중복] 회원가입 실패")
  @Test
  void SignUp_Fail_Duplicated_UserId() throws Exception {
    // given
    final UserSignUpRequestDto requestDto = signUpDto("");
    when(userService.signUp(requestDto)).thenReturn(new UserSignUpResponseDto());

    // when
    ResultActions resultActions = requestUserSaveDto(requestDto);

    // then
    resultActions.andExpect(status().isBadRequest());
  }

  @DisplayName("회원가입 성공")
  @Test
  void SignUp_Success() throws Exception {
    // given
    final UserSignUpRequestDto requestDto = signUpDto("");
    when(userService.signUp(requestDto)).thenReturn(new UserSignUpResponseDto());

    // when
    ResultActions resultActions = requestUserSaveDto(requestDto);

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
    ResultActions resultActions = requestUserLoginDto(requestDto);

    // then
    MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
    String accessToken = mvcResult.getResponse().getContentAsString();
    Assertions.assertThat(accessToken).isNotNull();
  }

  @DisplayName("[passWord 미입력] 로그인 실패")
  @Test
  void Login_Fail_Password_Blank() throws Exception {
    // given
    final UserLoginRequestDto requestDto = loginDto();
    when(userService.login(requestDto)).thenReturn(String.valueOf(new JwtResponseDto(null)));

    // when
    ResultActions resultActions = requestUserLoginDto(requestDto);

    // then
    MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
    String accessToken = mvcResult.getResponse().getContentAsString();
    Assertions.assertThat(accessToken).isNotNull();
  }

  @DisplayName("로그인 성공")
  @Test
  void Login_Success() throws Exception {
    // given
    final UserLoginRequestDto requestDto = loginDto();
    when(userService.login(requestDto)).thenReturn(String.valueOf(new JwtResponseDto(null)));

    // when
    ResultActions resultActions = requestUserLoginDto(requestDto);

    // then
    MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
    String accessToken = mvcResult.getResponse().getContentAsString();
    Assertions.assertThat(accessToken).isNotNull();
  }

  @DisplayName("제공된 URL을 통한 데이터 스크랩")
  @Test
  void User_Scrap() throws Exception  {
    // given
    final ScrapRequestDto requestDto = scrapDto();

    // when
    ResultActions resultActions = requestUserScrap(requestDto);

    // then
    resultActions.andExpect(status().isOk());
  }

  private UserSignUpRequestDto signUpDto(String type) {
    String userId = "userId";
    String password = "password";
    String name = "홍길동";
    String regNo = "860824-1655068";

    switch (type) {
      case "userId": userId = "";
        break;
      case "password": password = "";
        break;
      case "name": name = "";
        break;
      case "regNo": regNo = "";
        break;
    }
    return UserSignUpRequestDto.builder()
        .userId(userId)
        .password(password)
        .name(name)
        .regNo(regNo)
        .build();
  }

  private UserLoginRequestDto loginDto() {
    return UserLoginRequestDto.builder()
        .userId("userId")
        .password("password")
        .build();
  }

  private ScrapRequestDto scrapDto() {
    return ScrapRequestDto.builder()
        .name("홍길동")
        .regNo("860824-1655068")
        .build();
  }

  private ResultActions requestUserSaveDto(UserSignUpRequestDto requestDto) throws Exception {
    return mvc.perform(post("/api/szs/signup")
        .content(objectMapper.writeValueAsString(requestDto))
        .contentType(MediaType.APPLICATION_JSON));
  }

  private ResultActions requestUserLoginDto(UserLoginRequestDto requestDto) throws Exception {
    return mvc.perform(post("/api/szs/login")
        .content(objectMapper.writeValueAsString(requestDto))
        .contentType(MediaType.APPLICATION_JSON));
  }

  private ResultActions requestUserScrap(ScrapRequestDto requestDto) throws Exception {
    return mvc.perform(post("/api/szs/scrap")
        .content(String.valueOf(requestDto))
        .contentType(MediaType.APPLICATION_JSON));
  }


}