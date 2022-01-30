package szs.findrefund.service.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import org.mockito.junit.jupiter.MockitoExtension;
import szs.findrefund.common.exception.user.custom.UserNotFoundException;
import szs.findrefund.domain.user.User;
import szs.findrefund.domain.user.UserRepository;
import szs.findrefund.web.dto.scrap.ScrapRequestDto;
import szs.findrefund.web.dto.user.UserInfoResponseDto;
import szs.findrefund.web.dto.user.UserSignUpRequestDto;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static szs.findrefund.util.AESCryptoUtil.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  @InjectMocks
  private UserService userService;

  @Mock
  private UserRepository userRepository;

  @DisplayName("[아이디 중복] 유효성 검사 실패")
  @Test
  void Validate_Duplicate_Fail() throws Exception {
    // given
    final User requestUserDto = userDto();
    final String fakeUserId = "userId";
    doReturn(Optional.ofNullable(requestUserDto)).when(userRepository).findByUserId(fakeUserId);

    // when
    Method privateMethod = userService.getClass()
                                      .getDeclaredMethod("validateDuplicateUsers", String.class);
    privateMethod.setAccessible(true);

    // then
    assertThatExceptionOfType(InvocationTargetException.class)
        .isThrownBy(() -> privateMethod.invoke(userService, fakeUserId));
  }

  @DisplayName("[아이디 사용가능] 유효성 검사 성공")
  @Test
  void Validate_Duplicate_Success() throws Exception {
    // given
    final String fakeUserId = "userId";
    doReturn(Optional.empty()).when(userRepository).findByUserId(fakeUserId);

    // when
    Method privateMethod = userService.getClass()
                                      .getDeclaredMethod("validateDuplicateUsers", String.class);
    privateMethod.setAccessible(true);
    Object methodResult = privateMethod.invoke(userService, fakeUserId);

    // then
    assertThat(methodResult).isNull();
  }

  @DisplayName("[주민등록번호 잘못된 패턴] 유효성 검사 실패")
  @Test
  void Pattern_Not_Matched_RegNo() throws Exception {
    // given
    final String fakeRegNo = "860824--10000";

    // when
    Method privateMethod = userService.getClass()
                                      .getDeclaredMethod("patternMatchesRegNo", String.class);
    privateMethod.setAccessible(true);

    // then
    assertThatExceptionOfType(InvocationTargetException.class)
        .isThrownBy(() -> privateMethod.invoke(userService, fakeRegNo));
  }

  @DisplayName("[주민등록번호 올바른 패턴] 유효성 검사 성공")
  @Test
  void Pattern_Good_Matched_RegNo() throws Exception {
    // given
    final String fakeRegNo = "860824-1655068";

    // when
    Method privateMethod = userService.getClass()
                                      .getDeclaredMethod("patternMatchesRegNo", String.class);
    privateMethod.setAccessible(true);
    Object methodResult = privateMethod.invoke(userService, fakeRegNo);

    // then
    assertThat(methodResult.getClass()).isEqualTo(String.class);
  }

  @DisplayName("[가입 불가능한 회원] 회원가입 실패")
  @Test
  void Available_Not_User() throws Exception {
    // given
    final UserSignUpRequestDto requestDto = impossibleSignUpDto();

    // when
    Method privateMethod = userService.getClass()
        .getDeclaredMethod("availableUsers", UserSignUpRequestDto.class);
    privateMethod.setAccessible(true);

    // then
    assertThatExceptionOfType(InvocationTargetException.class)
        .isThrownBy(() -> privateMethod.invoke(userService, requestDto));
  }

  @DisplayName("[가입 가능한 회원] 회원가입 성공")
  @Test
  void Available_User() throws Exception {
    // given
    final UserSignUpRequestDto requestDto = encryptRegNoDto();

    // when
    Method privateMethod = userService.getClass()
        .getDeclaredMethod("availableUsers", UserSignUpRequestDto.class);

    privateMethod.setAccessible(true);
    Object methodResult = privateMethod.invoke(userService, requestDto);

    // then
    assertThat(methodResult).isNull();
  }

  @DisplayName("[내 정보] 조회 실패")
  @Test
  void Find_My_Info_Fail() {
    // given
    final Long IdFromToken = 1L;
    given(userRepository.findById(IdFromToken)).willThrow(new UserNotFoundException());

    // when

    // then
    assertThatExceptionOfType(UserNotFoundException.class)
        .isThrownBy(() -> userService.findMyInfo(IdFromToken));
  }

  @DisplayName("[내 정보] 조회 성공")
  @Test
  void Find_My_Info_Success() throws Exception {
    // given
    final Long IdFromToken = 1L;
    final User userDto = userDto();
    given(userRepository.findById(IdFromToken)).willReturn(Optional.ofNullable(userDto));

    // when
    UserInfoResponseDto myInfo = userService.findMyInfo(IdFromToken);

    // then
    assertThat(myInfo.getName()).isEqualTo(userDto.getName());
    assertThat(myInfo.getUserId()).isEqualTo(userDto.getUserId());
    assertThat(encrypt(myInfo.getRegNo())).isEqualTo(userDto.getRegNo());
  }

  @DisplayName("[URL 스크랩을 위한 정보] 조회 실패")
  @Test
  void Find_My_Info_For_Url_Fail() {
    // given
    final Long IdFromToken = 1L;
    given(userRepository.findById(IdFromToken)).willThrow(new UserNotFoundException());

    // when

    // then
    assertThatExceptionOfType(UserNotFoundException.class)
        .isThrownBy(() -> userService.findMyInfo(IdFromToken));
  }

  @DisplayName("[URL 스크랩을 위한 정보] 조회 성공")
  @Test
  void Find_My_Info_For_Url_Success() throws Exception {
    // given
    final Long IdFromToken = 1L;
    final User userDto = userDto();
    given(userRepository.findById(IdFromToken)).willReturn(Optional.ofNullable(userDto));

    // when
    ScrapRequestDto myInfo = userService.findMyInfoForUrlScrap(IdFromToken);

    // then
    assertThat(myInfo.getName()).isEqualTo(userDto.getName());
    assertThat(encrypt(myInfo.getRegNo())).isEqualTo(userDto.getRegNo());
  }

  @DisplayName("회원 객체 생성")
  private User userDto() throws Exception {
    return User.builder()
              .userId("userId")
              .password("password")
              .name("홍길동")
              .regNo(encrypt("860824-1655068"))
              .build();
  }

  @DisplayName("회원가입 객체 생성 - 주민등록번호 암호화 전")
  private UserSignUpRequestDto signUpDto() throws Exception {
    return UserSignUpRequestDto.builder()
               .userId("userId")
               .password("password")
               .name("홍길동")
               .regNo(encrypt("860824-1655068"))
               .build();
  }

  @DisplayName("회원가입 객체 생성 - 주민등록번호 암호화 후")
  private UserSignUpRequestDto encryptRegNoDto() throws Exception {
    return UserSignUpRequestDto.builder()
        .userId("userId")
        .password("password")
        .name("홍길동")
        .regNo("860824-1655068")
        .build();
  }

  @DisplayName("가입 불가능한 객체 생성")
  private UserSignUpRequestDto impossibleSignUpDto() throws Exception {
    return UserSignUpRequestDto.builder()
        .userId("userId")
        .password("password")
        .name("박기오")
        .regNo("860824-1655068")
        .build();
  }

}