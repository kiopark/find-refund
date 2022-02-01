# 삼쩜삼 회원의 숨은 환급금 찾기 서비스
> 삼쩜삼에 가입된 회원의 정보를 API 를 통해 스크랩하여 환급액이 있는지 조회하고,   
  조회된 금액을 계산하여 회원에게 환급정보를 알려줍니다.

## 목차
- [요구사항](#요구사항)
- [개발환경](#개발환경)
- [API](#API)
- [기능구현 방법](#기능구현-방법)
- [문제해결 전략](#문제해결-전략)
- [주관식 과제](#주관식-과제)

## 요구사항

* 과제 구현 시 Java, Spring Boot, JPA, H2, Gradle 을 빠짐없이 모두 활용합니다. DB는 H2 Embeded DB를 사용
* 회원가입, 환급액 계산, 유저 정보 조회 API를 구현
* 모든 요청에 대해 application/json 타입으로 응답
* 각 기능 및 제약사항에 대한 단위 테스트를 작성
* swagger를 이용하여 api 확인 및 api 실행 가능
* 민감정보(주민등록번호, 비밀번호) 등은 암호화된 상태로 저장
* README 파일을 이용하여 요구사항 구현 여부, 구현 방법 그리고 검증 결과에 대해 작성

## 개발환경
* Java 8 + Spring Boot 2.4.0
* H2 Embeded DB
* Gradle 7.3.3
* Spring Data JPA
* Junit5
* GitHub
* IntelliJ

## API
- **URL**
  `POST https://codetest.3o3.co.kr/scrap/`

## 기능구현 방법
- **회원가입**
  - 아이디, 이름, 비밀번호, 주민등록번호 를 필수값으로 입력 받습니다.
  - `Enum` 타입(AvailableUserEnum) 으로 등록되어있는 회원의 정보로만 가입이 가능합니다.
  
  ```java
  public enum AvailableUserEnum {
    홍길동("홍길동", "860824-1655068"),
    김둘리("김둘리", "921108-1582816"),
    마징가("마징가", "880601-2455116"),
    베지터("베지터", "910411-1656116"),
    손오공("손오공", "820326-2715702")
  }
  ```
  
  - 동일한 아이디의 회원이 존재하는지 중복회원 검사를 실시합니다.
  - 주민등록번호 형식의 적합여부를 위해 주민등록번호 유효성 검사(정규식 패턴검사)를 실시합니다.
  - 주민등록번호(AES256Crypto)와 비밀번호(BCryptPasswordEncoder)를 암호화하여 회원정보를 DB에 저장합니다.

- **로그인**
  - 아이디, 비밀번호를 입력받습니다.
  - 아이디값으로 DB에 정보를 조회하여 비밀번호가 일치하는지 비교합니다.
  - 로그인에 성공하면 `JWT`를 생성해 토큰값을 반환합니다.
  
  ```java
  return ResponseEntity.ok().body(new JwtResponseDto(jwtToken));
  ```
  
- **내 정보 조회**
  - JWT를 사용하여 회원을 식별하기 위해 HttpServeltRequest Header에 Authorization의 값으로 담습니다.
  
  ```html
  {
    Authorization: JWT Token
  }
  ```

  - 토큰 인증 절차를 거쳐 인증된 회원의 클레임에 저장된 아이디 값을 꺼냅니다.
  - 해당 아이디값으로 DB에서 내 정보를 조회합니다.
  
- **URL 스크랩**
  - 유효한 토큰인지 인증 작업을 합니다.
  - WebClient 를 통해 URL 스크랩 [API](#API)를 호출합니다.
  - API 요청이후 최대20초 가량의 대기시간이 있어 기존의 각 스레드간 Blocking방식처리를 [해결](#문제해결-전략) 하였습니다.
  - 또한, 대량의 트래픽이 발생할 경우 DB에 저장할 데이터 조회와 등록간의 데이터 꼬임 현상을 [방지](#문제해결-전략) 하였습니다.
  - API를 통해 가져온 `JSON` 형태의 데이터를 `ScrapResponseDto` 매핑합니다.
  - 매핑된 DTO 객체의 데이터를 분리합니다.   
    IncomeDetail(소득상세정보), IncomeClassfication(소득구분정보), ScrapStatus(스크랩상태정보), ScrapLog(스크랩로그정보)
  - @OneToOne(연관관계의 주인 - Income)을 통해 각각의 DB에 데이터를 저장합니다.
  
- **환급액 조회**
  - 토큰 인증 절차를 거쳐 인증된 회원의 클레임에 저장된 아이디 값을 꺼냅니다.
  - 해당 아이디값으로 내 정보를 조회합니다.
  - 조회된 정보의 주민등록번호(Unique) 값으로 소득내역을 조회합니다.
  - 조회된 데이터를 이용하여 한도액, 공제액, 환급액을 계산하여 반환합니다.
  - URL 스크랩과 환급액 조회가 동시다발적으로 이루어 질때 불일치할 수 있는 데이터를 일관성 있게 유지 되도록 [개선](#문제해결-전략) 하였습니다.

## 문제해결 전략
  - Non-Blocking HTTP 요청을 위해 `WebClient`를 사용하여 요청후 [무작정 기다려 길어지는 응답시간 문제](#기능구현-방법)를 해결하였습니다.
  - Method Level 에서 @Cacheable 으로 특정값(accessToken)을 캐싱처리하여 [동일한 데이터로 동일한 동작을 하는 문제](#기능구현-방법)를 사전 방지하였습니다.
  - 중복 저장을 방지하기위해 DB에 이미 동일한 회원의 정보가 존재하면 해당 데이터를 삭제 후 저장합니다.
  - WebClient의 `onStatus()` 체이닝을 이용하여 API의 에러 처리를 하였습니다.
  - [서버 장애나 트래픽 문제](#기능구현-방법)로 인해 데이터가 불일치 할 수 있다는 문구를 보여주어 내부적으로 처리를 하고있다는 응답을 하였습니다.

## 주관식 과제
1. **이벤트 드리븐 기반으로 서비스를 만들 때 이벤트를 구성하는 방식과 실패 처리하는 방식에 대해 서술해 주세요.**
  - 서비스 간의 통신에 `Event Queue` 를 두는 방법을 이용합니다.   
  > - 각각의 서비스에 `producer`와 `consumer`가 있습니다. producer의 행위로 인해 메시지가 Event Queue로 전송됩니다.   
      이를 구독하던 consumer가 메세지를 수신하여 작업을 진행합니다. 이때, producer는 구독자의 주소나 수신여부를 몰라도 되며,   
      장애가 발생해도 그 지점부터 재처리를 할 수있고 많은 양의 `실시간 스트림 데이터`를 효과적으로 처리할 수 있습니다.   
  > - 트랜잭션의 실패가 일어날 떄, 실패 이벤트를 Event Queue로 전송하여 이를 구독하던 서비스는 취소 프로세스를 진행 시킵니다.   
    
2. **MSA 구성하는 방식에는 어떤 것들이 있고, 그중 선택하신다면 어떤 방식을 선택하실 건가요?** 
  - 방식
  > - 발송자가 수신자에게 메세지를 전송하고 네트워크 오류를 비롯한 메세지 전송 실패시 발송자가 다시 재전송을 하는 `단일 수신자 메시지 통신` 방식   
  > - 발송자가 `메세지 브로커`로 전송하고 수신자가 이를 직접 소비하는 방식의 결합도가 낮은 `다중 수신자 메시지 통신` 방식   
  - 내부 서비스 간에는 비동기 `메세징 큐`를 이용하여 통신을 하고, 외부 서비스와는 API 통신을하는 `다중 수신자 메시지 통신` 방식을 선택하겠습니다.
  > - 서비스간의 커플링이 없어 추가와 삭제가 용이합니다.   
  > - 서버간의 데이터를 주고받을때는 항상 시스템 장애를 염두해 두어야 합니다. 메세징 큐에 쌓인 메시지는 컨슈머가 소비하기 전까지 보관됩니다. 

