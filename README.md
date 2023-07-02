# Convention

## 브랜치 전략

- 기능 브랜치
    - feature/#2_view_user
- hotfix 브랜치
    - hotfix/#2_view_user
- release 브랜치
    - release/v1.0.0
- master 브랜치
    - master
- develop 브랜치
    - develop

## 깃 메시지

```java
################
# <타입>: <제목> 의 형식으로 제목을 아래 공백줄에 작성
# 제목은 50자 이내 / 변경사항이 "무엇"인지 명확히 작성 / 끝에 마침표 금지
# 예) [Feat] #1 로그인 기능 추가

# 바로 아래 공백은 제목과 본문의 분리를 위함

################
# 본문(구체적인 내용)을 아랫줄에 작성
# 여러 줄의 메시지를 작성할 땐 "-"로 구분 (한 줄은 72자 이내)

# 바로 아래 공백은 본문과 꼬릿말의 분리를 위함

################
# 꼬릿말(footer)을 아랫줄에 작성 (현재 커밋과 관련된 이슈 번호 추가 등)
# 예) Close #7

################
#Feat
#새로운 기능 추가
#
#Fix
#버그 수정
#
#Design
#CSS 등 사용자 UI 디자인 변경
#
#!BREAKING CHANGE
#커다란 API 변경의 경우
#
#!HOTFIX
#급하게 치명적인 버그를 고쳐야하는 경우
#
#Style
#코드 포맷 변경, 세미 콜론 누락, 코드 수정이 없는 경우
#
#Refactor
#프로덕션 코드 리팩토링
#
#Comment
#필요한 주석 추가 및 변경
#
#Docs
#문서 수정
#
#Test
#테스트 추가, 테스트 리팩토링(프로덕션 코드 변경 X)
#
#Chore
#빌드 테스트 업데이트, 패키지 매니저를 설정하는 경우(프로덕션 코드 변경 X)
#
#Rename
#파일 혹은 폴더명을 수정하거나 옮기는 작업만인 경우
#
#Remove
#파일을 삭제하는 작업만 수행한 경우
################
```

## 코드 컨벤션

### 1. 변수명

- 카멜케이스 사용
- 한국어 발음 대로의 표기 금지
- 클래스명 - 명사형 (ex. Controller Service)
- 메서드명 - 동사형 (ex. `changeName()`)
- 상수(static final 키워드)명 - 상수 이름은 대문자로 작성하며, 복합어는 언더스코어 `_`
를 사용

### 2. 주석

- 적당히 설명이 필요할 것 같은 메서드에 대해 주석을 작성한다.
    - 이 메서드가 어떤 클래스의 무슨 메서드로부터 호출 되었는지.
    - 이 메서드의 호출 흐름이 어떻게 되는지 등등.

### 3. 줄바꿈

- 파라미터가 줄바꿈이 필요할 만큼 여러개 존재 시, 컴마 뒤에서 줄을 바꾼다.
- `.` 을 앞에 둔다.
- 중괄호
    
    앞 구문의 끝 중괄호 `}` 다음에 작성한다.
    
    ```java
    try {
        writeLog();
    } catch (IOException ioe) {
        reportFailure(ioe);
    } finally {
        writeFooter();
    }
    ```
    
- 메서드 사이 빈 줄 삽입

### 4. Gradle 빌드 파일

- 의존 라이브러리에 대한 설명 주석 추가
    
    ```java
    // 이메일 전송 관련
    implementation 'org.springframework.boot:spring-boot-starter-mail'
    
    // thymeleaf
    implementation 'org.springframework.boot:spring-boot-starter-thymeleaf'
    ```
    
- `application.yml` or `application.properties`
    
    설명 `#` 주석 추가하기