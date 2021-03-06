package szs.findrefund.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import szs.findrefund.common.enums.AvailableUserEnum;
import szs.findrefund.common.enums.SignUpEnum;
import szs.findrefund.common.exception.user.custom.*;
import szs.findrefund.domain.user.User;
import szs.findrefund.domain.user.UserRepository;
import szs.findrefund.util.JWTUtil;
import szs.findrefund.web.dto.scrap.ScrapRequestDto;
import szs.findrefund.web.dto.user.*;

import java.util.regex.Pattern;

import static szs.findrefund.common.Constants.PatternConst.REGIST_REG_NO_RULE;
import static szs.findrefund.util.AESCryptoUtil.*;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  /**
   * 회원가입
   */
  @Transactional
  public UserSignUpResponseDto signUp(UserSignUpRequestDto requestDto) throws Exception {

    availableUsers(requestDto);
    validateDuplicateUsers(requestDto);
    String encryptRegNo = patternMatchesRegNo(requestDto.getRegNo());
    requestDto.encryptTheRegNo(encryptRegNo);
    requestDto.encryptThePassword(passwordEncoder.encode(requestDto.getPassword()));

    Long success = userRepository.save(requestDto.toEntity()).getId();
    return SignUpEnum.signUpResult(success);
  }

  /**
   * 주민등록번호 유효성 검사
   */
  private String patternMatchesRegNo(String regNo) throws Exception {
    boolean matches = Pattern.matches(REGIST_REG_NO_RULE, regNo);

    if (matches) {
      return encrypt(regNo);
    } else {
      throw new RegNoNotMatchException();
    }
  }

  /**
   * 가입 가능한 회원 검사
   */
  private void availableUsers(UserSignUpRequestDto requestDto) {
    AvailableUserEnum.find(requestDto)
        .orElseThrow(NonAvailableUserException::new);
  }

  /**
   * 중복회원 검사
   */
  private void validateDuplicateUsers(UserSignUpRequestDto requestDto) throws Exception {
    userRepository.findByUserId(requestDto.getUserId())
                  .ifPresent(user -> {
                    throw new ValidDuplicatedIdException();
                  });

    userRepository.findByRegNo(encrypt(requestDto.getRegNo()))
                  .ifPresent(user -> {
                    throw new ValidDuplicatedUserException();
                  });
  }

  /**
   * 로그인
   */
  @Transactional
  public String login(UserLoginRequestDto requestDto) {
    User findUser = userRepository.findByUserId(requestDto.getUserId())
                                  .orElseThrow(UserNotFoundException::new);

    boolean matches = passwordEncoder.matches(requestDto.getPassword(), findUser.getPassword());
    if (!matches) {
      throw new PassWordNotMatchException();
    }

    return JWTUtil.createJwt(findUser);
  }

  /**
   * 내 정보 조회
   */
  @Transactional(readOnly = true)
  public UserInfoResponseDto findMyInfo(Long idFromToken) throws Exception {
    User findUser = userRepository.findById(idFromToken)
                                  .orElseThrow(UserNotFoundException::new);

    return UserInfoResponseDto.builder()
                              .userId(findUser.getUserId())
                              .name(findUser.getName())
                              .regNo(decrypt(findUser.getRegNo()))
                              .build();
  }

  /**
   * URL 스크랩 위한 정보 조회
   */
  @Transactional(readOnly = true)
  public ScrapRequestDto findMyInfoForUrlScrap(Long idFromToken) throws Exception {
    User findUser = userRepository.findById(idFromToken)
                                  .orElseThrow(UserNotFoundException::new);

    return ScrapRequestDto.builder()
                          .name(findUser.getName())
                          .regNo(decrypt(findUser.getRegNo()))
                          .build();
  }

}
