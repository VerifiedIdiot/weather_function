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

## application.properties 설정
![image](https://github.com/VerifiedIdiot/weather_function/assets/107241795/03e4ce5e-535d-48ff-85d0-8536f8541035)

MySQL 서버의 계정과 비밀번호, 기상청API허브(https://apihub.kma.go.kr/)에서 API키 발급

 
## 변경 사항
- Java 11 -> Java 17
- Spring Boot 2.5.5 -> Spring Boot 3.2.5
- RestTemplate -> RestClient -> HttpClient
- springfox-swagger2:2.9.2 -> springdoc-openapi-starter-webmvc-ui:2.0.2

## 개선 사항
- Swagger 2.0에서 springdoc-openapi로의 업그레이드로 OpenAPI Specification 3.0 및 Spring 통합을 개선하여 API 문서 생성 및 관리를 향상 
- 스프링부트 3.x.x 부터 더이성 지원지 중단된 RestTemplate 대신 RestClient사용 및 코드간소화 -> 비교적 저수준의 방식인 HttpClient로 변경하여 api요청 응답시간을 개선
- 반복된 외부 API호출로 인한 지연시간을 Async를 통해 비동기 처리
- 데이터 insert 시 flush & clear 사용 대신 saveAll로 일괄처리
