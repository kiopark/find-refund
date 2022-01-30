# 삼쩜삼 회원의 숨은 환급금 찾기 서비스
> 삼쩜삼에 가입된 회원의 정보를 API 를 통해 스크랩하여 환급액이 있는지 조회하고,   
  조회된 금액을 계산하여 회원에서 환급액 정보를 알려줍니다.

## 목차
- [요구사항](#요구사항)
- [개발환경](#개발환경)
- [API](#API)
- [기능구현 방법](#기능구현방법)
- [문제해결 전략](#문제해결전략)
- [주관식 과제](#주관식과제)

## 요구사항

* 과제 구현 시 Java, Spring Boot, JPA, H2, Gradle 을 빠짐없이 모두 활용합니다. DB는 H2 Embeded DB를 사용
* 회원가입, 환급액 계산, 유저 정보 조회 API를 구현
* 모든 요청에 대해 application/json 타입으로 응답
* 각 기능 및 제약사항에 대한 단위 테스트를 작성
* swagger를 이용하여 api 확인 및 api 실행 가능
* 민감정보(주민등록번호, 비밀번호) 등은 암호화된 상태로 저장
* README 파일을 이용하여 요구사항 구현 여부, 구현 방법 그리고 검증 결과에 대해 작성

## 개발환경
* Java 11 + Spring Boot 2.4.0
* H2 Embeded DB
* Gradle 7.3.3
* Spring Data JPA
* Junit5
* GitHub
* IntelliJ

## API

## 기능구현방법
- **회원가입**
  - 아이디, 이름, 비밀번호, 주민등록번호 를 필수값으로 입력 받는다.
  - Enum 타입(AvailableUserEnum) 으로 등록되어있는 회원의 정보로만 가입이 가능하다.
  
  ```java
  public enum AvailableUserEnum {
    홍길동("홍길동", "860824-1655068"),
    김둘리("김둘리", "921108-1582816"),
    마징가("마징가", "880601-2455116"),
    베지터("베지터", "910411-1656116"),
    손오공("손오공", "820326-2715702")
  }
  ```
  
  - 동일한 아이디의 회원이 존재하는지 중복회원 검사를 실시한다.
  - 주민등록번호 형식의 적합여부를 위해 주민등록번호 유효성 검사(정규식 패턴검사)를 실시한다.
  - 주민등록번호(AES256Crypto) 와 비밀번호(BCryptPasswordEncoder)를 암호화하여 회원정보를 DB에 저장한다.

- **로그인**
  - 아이디, 비밀번호를 입력받는다.
  - 아이디값으로 DB에 정보를 조회하여 비밀번호가 일치하는지 비교한다.
  - 로그인에 성공하면 JWT 를 생성해 토큰값을 반환한다.
  
  ```java
  return ResponseEntity.ok().body(new JwtResponseDto(jwtToken));
  ```
  
- **내 정보 조회**
  - JWT를 사용하여 회원을 식별하기 위해 HttpServeltRequest Header에 Authorization의 값으로 담는다.
  
  ```java
  {
    Authorization: JWT Token
  }
  ```

  - 토큰 인증 절차를 거쳐 인증된 회원의 클레임에 저장된 아이디 값을 꺼낸다.
  - 해당 아이디값으로 DB에서 내 정보를 조회한다.
  
- **URL 스크랩**
  - 유효한 토큰인지 인증 작업을 한다.
  - WebClient 를 통해 URL 스크랩 [API](#API)를 호출한다.
  - API를 통해 가져온 JSON 형태의 데이터를 ScrapResponseDto 매핑한다.
  - 매핑된 DTO 객체의 데이터를 분리하여 저장한다   
    IncomeDetail(소득상세정보), IncomeClassfication(소득구분정보), ScrapStatus(스크랩상태정보), ScrapLog(스크랩로그정보)
  
  
## 문제해결전략

## 주관식과제
