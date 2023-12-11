
![background-7269660_1280](https://github.com/Wanted-Pre-Onboarding-Backend7-R/spend-tracker/assets/110372498/19310f8c-5cc8-4d3a-b074-0caa2161bc83)
# 🏦 예산 관리 어플리케이션, Spend Tracker
사용자의 개인 재무를 관리하고, 지출을 모니터링 하는 애플리케이션

<br/>

## 목차
- [프로젝트 소개](#프로젝트-소개)
- [ERD 설계](#erd-설계)
- [API 설계](#api-설계)
- [프로젝트 일정관리](#프로젝트-일정관리)
- [요구사항 분석](#요구사항-분석)
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
  
![SpringBoot](https://img.shields.io/badge/SpringBoot-6DB33F?style=for-the-badge&logo=SpringBoot&logoColor=white) 
![Java](https://img.shields.io/badge/Java-007396.svg?&style=for-the-badge&logo=Java&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens) 

![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=MySQL&logoColor=white) 
![Redis](https://img.shields.io/badge/Redis-DC382D?style=for-the-badge&logo=redis&logoColor=white)
![Swagger](https://img.shields.io/badge/swagger-%ffffff.svg?style=for-the-badge&logo=swagger&logoColor=white)


</div>

<br/>
<br/>


## 컨벤션 규칙

<details>
<summary>Code 컨벤션 - click</summary><br>


- **Code 컨벤션** 
  - 변수명: boolean-형용사, 그 외 -명사
  - 함수명: 동사 현재형으로 시작
  - 클래스명: 명사
  - if, for 중괄호 사용
  - 커밋 전
     - import 정리: `ctrl + alt(option) + o`
     - line formatting: `ctrl(command) + alt(option) + l`
     - 마지막 빈 줄 추가
    
    ```java
    /** 예시 **/
    public class Clazz {

        public int addCountIfValid(int count, boolean isValid) {
            if (isValid) {
                return count + 1;
            }
            return count;
        }
    }
    // 마지막 빈 줄
    ```
    
  - Optional 줄바꿈
     ```java
     Member member = memberRepository.findByEmail(dto.getEmail())
           .orElseThrow(NotFoundMemberByEmailException::new);`
     ```
  - 객체 생성 규칙
    - 외부에서 직접적인 new 지양하고 내부적으로 활용 `@Builder` 및 정적 팩토리 메서드 활용
    - 정적 팩토리 메서드 이름은 단일 인자일 경우 `from`, 다중 인자일 경우는 `of`로 명명 
    - Bean 제외 DTO, Entity들은 `@All-/@Required-ArgsContructor` 활용 제한, 직접 코드로 생성자 작성 및 private/protected 등으로 잠그기
    - 목적: 같은 타입의 필드 연속될 때 1) 잘못된 값 입력하는 human error 최소화, 2) 필드 순서를 바꿀 경우 IDE에 의한 리팩토링이 적용되지 않는 Lombok 에러 방지, 3) 가독성을 위한 작성법 통일을 위하여
    ```java
     @Getter
     @NoArgsConstructor(access = AccessLevel.PROTECTED)
     @Entity
     public class Member extends BaseEntity {
   
        ... (생략) ...
     
         @Builder
         private Member(String accountName, String email, String password, String approvalCode, Boolean isApproved) {
             this.accountName = accountName;
             this.email = email;
             this.password = password;
             this.approvalCode = approvalCode;
             this.isApproved = isApproved;
             authority = Authority.ROLE_USER;
         }
   
         public static Member of(MemberJoinRequest dto, String encodedPassword, String approvalCode) {
             return builder()
                     .accountName(dto.getAccountName())
                     .email(dto.getEmail())
                     .password(encodedPassword)
                     .approvalCode(approvalCode)
                     .isApproved(false)
                     .build();
         }
     }
  
    ```

</details>

<details>
<summary>Git 컨벤션 - click</summary><br>

- **git commit rules**
  
    | type     | description |
    |----------|-------------|
    | feat     | 새로운 기능 추가 |
    | fix      | 버그 및 로직 수정 |
    | refactor | 기능 변경 없는 코드 구조, 변수/메소드/클래스 이름 등 수정 |
    | style    | 코드 위치 변경 및 포맷팅, 빈 줄 추가/제거, 불필요한 import 제거 |
    | test     | 테스트 코드 작성 및 리팩토링 |
    | setup    | build.gradle, application.yml 등 환경 설정 |
    | docs     | 문서 작업 |
  
    ```bash
    # commit title format
    git commit -m "{커밋 유형} #{이슈번호}: #{내용}"
  
    # example of git conventions
    git commit -m "refactor #125: `ChatService` 중복 로직 추출
  
    ```

- **git branch rules**
    ```bash
    # branch name format
    git checkout -b "feat/#{이슈번호}-{내용}"
    ```


</details>


<br/>


## ERD 설계

![spendTracker (2)](https://github.com/Wanted-Pre-Onboarding-Backend7-R/spend-tracker/assets/110372498/44b7a056-8b60-4ab9-9e41-35c90cb15fa6)



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


## 요구사항 분석


<br/>
<br/>

## TIL 및 회고



<br/>
<br/>



