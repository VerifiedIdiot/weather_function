# 날씨기능 (스프링부트)
- 사이드 프로젝트에서 구현했던 날씨 기능을 모듈화 및 마이그레이션
- 불필요한 라이브러리 및 코드 제거
 
## 사용 스택
- Java
- Spring Boot
- Hibernate JPA
- MySQL
- Swagger

## 스웨거 주소
- Swagger: (http://localhost:8080/swagger-ui/index.html#)

## 프로젝트 구조
 - 프로젝트 구조는 과거 사이드프로젝트에서 모듈화만 진행한 legacy, 마이그레이션과 리팩토링이 진행된 refactored로 구분이된다.
![image](https://github.com/VerifiedIdiot/weather_function/assets/107241795/f11adb04-4c73-4b7f-8a57-49882924dd71)


## application.properties 설정
![image](https://github.com/VerifiedIdiot/weather_function/assets/107241795/03e4ce5e-535d-48ff-85d0-8536f8541035)

MySQL 서버의 계정과 비밀번호, 기상청API허브(https://apihub.kma.go.kr/)에서 API키 발급

 
## 변경 사항
- Java 11 -> Java 17
- Spring Boot 2.5.5 -> Spring Boot 3.2.5
- RestTemplate -> RestClient -> WebClient
- springfox-swagger2:2.9.2 -> springdoc-openapi-starter-webmvc-ui:2.0.2

## 개선 사항
- Swagger 2.0에서 springdoc-openapi로의 업그레이드로 OpenAPI Specification 3.0 및 Spring 통합을 개선하여 API 문서 생성 및 관리를 향상 
- 스프링부트 3.x.x 부터 더이성 지원지 중단된 RestTemplate 대신 RestClient사용 및 코드간소화 -> 비동기방식으로 쓰레드 갯수를 조절하여 WebClient로 대체
- 로직이 실행되는동안 API 응답 지연으로 인해 대부분의 시간이 할애됨, 그래서 비동기 요청처리를 통해 쓰레드를 조절하여 시간을 단축
- 데이터 insert 시 flush & clear 사용 대신 saveAll로 일괄처리

## 결과 
- RestTemplate을 통해 동기적으로 작동하던 API요청을 WebClient의 비동기적인 방식을 통해 다음과 같이 작업시간이 단축되었다.
Legacy코드 : Duration: 256 seconds -> Refactored코드 : Duration: 146 seconds


