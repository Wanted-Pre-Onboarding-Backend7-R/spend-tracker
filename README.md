
![background-7269660_1280](https://github.com/Wanted-Pre-Onboarding-Backend7-R/spend-tracker/assets/110372498/19310f8c-5cc8-4d3a-b074-0caa2161bc83)
# 🏦 예산 관리 어플리케이션, Spend Tracker
사용자의 개인 재무를 관리하고, 지출을 모니터링 하는 애플리케이션

<br/>

## 목차
- [프로젝트 소개](#프로젝트-소개)
- [ERD 설계](#erd-설계)
- [API 설계](#api-설계)
- [프로젝트 일정관리](#프로젝트-일정관리)
- [API 구현과정 및 고려사항](#api-구현과정-및-고려사항)
- [TIL 및 회고](#til-및-회고)


<br/>


## 프로젝트 소개
본 서비스는 사용자들이 `개인 재무를 관리`하고 `지출을 추적`하는 데 도움을 주는 애플리케이션입니다.<br>
이 앱은 사용자들이 `예산을 설정`하고 `지출을 모니터링`하며 재무 목표를 달성하는 데 도움이 됩니다. 

`주요기능`
1. 사용자는 본 사이트에 들어와 `회원가입`을 통해 서비스를 이용합니다.
2. 사용자는 `예산을 설정`하고 `지출을 모니터링`합니다.
3. `예산 추천`은 서비스를 이용하는 사용자들의 통계를 바탕으로 예산을 추천합니다.
4. `지출 컨설팅`은 설정한 예산을 기준으로 금일 `소비 가능한 지출`과 `발생한 지출`을 `총액`과 `카테고리별`로 안내 합니다.
    - 금일 지출 추천 금액과 사용 금액의 차이를 `%`로 `위험도`를 보여줍니다.

`추후 구현`
- 지출 통계
    - 지난 달 대비 총액, 카테고리 별 소비율
    - 지난 요일 대비 소비율
    - 다른 사용자 대비 소비율

<br/>


## Skills
<div align="center">
    <img src="https://img.shields.io/badge/Java-007396.svg?&style=for-the-badge&logo=Java&logoColor=white">
    <img src="https://img.shields.io/badge/SpringBoot-6DB33F?style=for-the-badge&logo=SpringBoot&logoColor=white">
    <img src="https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens"><br/>
    <img src="https://img.shields.io/badge/Spring Data JPA-gray?style=for-the-badge&logoColor=white"/>
    <img src="https://img.shields.io/badge/QueryDSL-0078D4?style=for-the-badge&logo=Spring Data JPA&logoColor=white"/><br/>
    <img src="https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=MySQL&logoColor=white">
    <img src="https://img.shields.io/badge/Redis-DC382D?style=for-the-badge&logo=redis&logoColor=white">
    <img src="https://img.shields.io/badge/swagger-%ffffff.svg?style=for-the-badge&logo=swagger&logoColor=white"><br/>
</div>

<br/>
<br/>


## 컨벤션 규칙

<details>
<summary>Code 컨벤션 (click) </summary><br>


- **Code 컨벤션** 
>  - 변수명: boolean-형용사, 그 외 -명사
>  - 함수명: 동사 현재형으로 시작
>  - 클래스명: 명사
>  - if, for 중괄호 사용
>  - 커밋 전
>     - import 정리: `ctrl + alt(option) + o`
>     - line formatting: `ctrl(command) + alt(option) + l`
>     - 마지막 빈 줄 추가
>    
>    ```java
>    /** 예시 **/
>    public class Clazz {
>
>        public int addCountIfValid(int count, boolean isValid) {
>            if (isValid) {
>                return count + 1;
>            }
>            return count;
>        }
>    }
>    // 마지막 빈 줄
>    ```
>    
>  - Optional 줄바꿈
>     ```java
>     Member member = memberRepository.findByEmail(dto.getEmail())
>           .orElseThrow(NotFoundMemberByEmailException::new);`
>     ```
>  - 객체 생성 규칙
>    - 외부에서 직접적인 new 지양하고 내부적으로 활용 `@Builder` 및 정적 팩토리 메서드 활용
>    - 정적 팩토리 메서드 이름은 단일 인자일 경우 `from`, 다중 인자일 경우는 `of`로 명명 
>    - Bean 제외 DTO, Entity들은 `@All-/@Required-ArgsContructor` 활용 제한, 직접 코드로 생성자 작성 및 private/protected 등으로 잠그기
>    - 목적: 같은 타입의 필드 연속될 때 1) 잘못된 값 입력하는 human error 최소화, 2) 필드 순서를 바꿀 경우 IDE에 의한 리팩토링이 적용되지 않는 Lombok 에러 방지, 3) 가독성을 위한 작성법 통일을 위하여
>    ```java
>     @Getter
>     @NoArgsConstructor(access = AccessLevel.PROTECTED)
>     @Entity
>     public class Member extends BaseEntity {
>   
>        ... (생략) ...
>     
>         @Builder
>         private Member(String accountName, String email, String password, String approvalCode, Boolean isApproved) {
>             this.accountName = accountName;
>             this.email = email;
>             this.password = password;
>             this.approvalCode = approvalCode;
>             this.isApproved = isApproved;
>             authority = Authority.ROLE_USER;
>         }
>   
>         public static Member of(MemberJoinRequest dto, String encodedPassword, String approvalCode) {
>             return builder()
>                     .accountName(dto.getAccountName())
>                     .email(dto.getEmail())
>                     .password(encodedPassword)
>                     .approvalCode(approvalCode)
>                     .isApproved(false)
>                     .build();
>         }
>     }
>  
>    ```

</details>

<details>
<summary>Git 컨벤션 (click)</summary><br>

- **git commit rules**
>  
>    | type     | description |
>    |----------|-------------|
>    | feat     | 새로운 기능 추가 |
>    | fix      | 버그 및 로직 수정 |
>    | refactor | 기능 변경 없는 코드 구조, 변수/메소드/클래스 이름 등 수정 |
>    | style    | 코드 위치 변경 및 포맷팅, 빈 줄 추가/제거, 불필요한 import 제거 |
>    | test     | 테스트 코드 작성 및 리팩토링 |
>    | setup    | build.gradle, application.yml 등 환경 설정 |
>    | docs     | 문서 작업 |
>  
>    ```bash
>    # commit title format
>    git commit -m "{커밋 유형} #{이슈번호}: #{내용}"
>  
>    # example of git conventions
>    git commit -m "refactor #125: `ChatService` 중복 로직 추출
>  
>    ```
>
- **git branch rules**
>    ```bash
>    # branch name format
>    git checkout -b "feat/#{이슈번호}-{내용}"
>    ```


</details>


<br/>


## ERD 설계

![spendTracker (2)](https://github.com/Wanted-Pre-Onboarding-Backend7-R/spend-tracker/assets/110372498/f9c2a13f-4670-44b6-8ff0-74010640b6e0)




<br/>
<br/>


## API 설계
Swagger:

[![Notion](https://img.shields.io/badge/Notion-%23000000.svg?style=for-the-badge&logo=notion&logoColor=white)](https://lush-hen-bac.notion.site/c5f8920838424f85b2ba788bea3225d8?v=3f3d2d66d78942da96ccdab24bbde882&pvs=4)



<br/>
<br/>

## 프로젝트 일정관리

<img width="700" alt="스크린샷 2023-12-11 오후 5 31 14" src="https://github.com/Wanted-Pre-Onboarding-Backend7-R/spend-tracker/assets/110372498/f37091a9-8bf4-4c39-bbe7-44905ba7e931">


<br/>
<br/>


## API 구현과정 및 고려사항

<details>
<summary>사용자 (click) </summary><br>

### 사용자 회원가입(API)

- `계정명` , `패스워드` 입력
- 계정명
    - 중복불가
    - 최소 2 ~ 최대 20자
- 패스워드
    - 길이가 10 이하
    - 3회 이상 반복되는 문자 포함 ❌
    - 숫자, 영문, 특수문자 중 2가지 이상을 포함
    - 개인 정보와 유사한 비밀번호 ❌

### 사용자 로그인(API)

- 로그인 시 `AccessToken` 과 `RefreshToken` 발급
- `RefreshToken`은 `redis`에 저장하여 관리
- 이후 모든 API 요청 Header 에 `JWT` 가 항상 포함되며, `JWT` 유효성 검증

### 사용자 로그아웃(API)

- 로그아웃시 `redis`에 저장된 `RefreshToken` 을 삭제하고, `key : value = 사용자명 : AccessToken` 형식으로 저장하여 로그아웃 사용자 관리
- 모든 요청 전의 `jwtAuthenticationFilter` 에서 로그아웃 사용자일 시 접근 불가한 

<br/>
<br/>
    
</details>

<details>
<summary>예산 (click) </summary><br>

## 카테고리

### 카테고리 목록 조회(API)

- 카테고리 테이블에 있는 전체 카테고리 목록 반환

<br/>

## 예산

### 예산 설정(API)

- `해당 월`, `카테고리`, `금액` 필수 포함하여 예산 설정
    - `해당 월` : 최소 1 ~ 최대 12 범위 제한
    - `금액` : 양수 및 100원 단위로 범위 제한

### 예산 추천(API)

- 카테고리 지정없이 `총액` 입력 시 카테고리 별 예산 자동으로 생성하여 추천
    - 자동 생성된 예산은 기존 사용자들의 카테고리 별 예산을 통계하여 설정한 평균 값
        - 예) 기존 사용자들이 평균적으로 40% 를 `식비`에, 30%를 `주거` 에 설정하였다면 이에 맞게 추천
        - 추천 한 예산 금액은 `백의 자리에서 반올림`하여 반환
        
        > **추가설명**
        
        사용자는 예산 설정 페이지 에서 카테고리별로 `예산을 설정` 합니다. 
        이를 지정하기 어려운 사용자들은 `예산 추천 기능`을 사용하여 카테고리별 예산 금액을 추천 받고, 저장 버튼을 클릭 시 Front에서 `예산 설정 API`에 값들을 넘겨 예산을 설정하도록 합니다.
        >
<br/>
<br/>

</details>

<details>
<summary>지출 (click) </summary><br>

## 지출 기록

### 지출 속성

- `지출 일시`, `지출 금액`, `카테고리` , `메모` 와 `합계 제외 여부` 를 가지고 있음
    - 추가적인 필드 자유롭게 사용

### 지출 CRUD (API)

- 지출을 `생성`, `수정`, `읽기(상세)`, `읽기(목록)`, `삭제` , `합계 제외 여부` 설정 가능
    - `지출 금액`: 100원 단위로 범위 제한
    - 생성 시, `카테고리 id` 로 해당 카테고리 존재 여부 확인
    - 수정 시, `지출 id` 로 해당 지출 내역 존재 여부 확인, 해당 작성자인지 확인, `PATCH` 로 부분 수정
    - 상세 조회 및 삭제 시, `지출id` 로 해당 지출 내역 존재 여부 확인, 해당 작성자인지 확인
- `읽기(목록 조회)`
    - 조회 시 필터링 조건
        - `지출 기간` ,`지출 카테고리` ,`지출 최소 금액` ,`지출 최대 금액` 으로 조회
        - 조회된 모든 지출 내역 `총 지출 합계`, `카테고리 별 지출 합계` 같이 반환
        - `합계 제외` 처리한 지출은 목록에 포함되지만 `총 지출 합계`, `카테고리 별 지출 합계`에서 제외
        - 지출 시간으로 `내림차순` 하여 반환 (최신순)
    - 페이징 처리하여 `총 조회 내역 건수` 와 `총 페이지 수` 함께 반환
<br/>

## 지출 컨설팅

### 지출 추천 (API)

- `월별` 예산을 만족시키는 오늘 지출 가능한 금액을 `총액` 과 `카테고리 별 금액` 으로 추천
    - 추천 지출 총액 : `이번 달 총 예산 금액`에서 `오늘까지의 지출 금액`을 뺀 금액을 `이번 달 남은 날짜의 수`로 나누어 반환
    - 카테고리 별 추천 지출 금액 :
        - `이번 달 총 예산 금액`에 따른 `카테고리 별 예산 금액`의 비율을 기준으로 계산하여 반환
        - 십의 자리에서 반올림
    - `추천 지출 총액`이 `0`보다 작거나(음수), 같으면 `최소 추천 금액`을 반환, `카테고리 별 추천 지출 금액`도 `최소 추천 금액`을 기준으로 계산하여 반환

### 지출 안내 (API)

- 오늘 지출한 `총액`과 `카테고리 별 지출 총액`, `지출 비율(위험도)`을 반환
- `지출 비율(위험도)`
    - `지출 총액` 기준 오늘 지출 가능한 금액 대비 실제 쓴 지출의 비율(%)
    - 소수점 첫 째 자리에서 반올림

<br/>
<br/>

</details>

<br/>
<br/>

## TIL 및 회고



<br/>
<br/>



