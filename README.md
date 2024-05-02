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

## 사전 준비 사항 
- MySQL서버 설정, API KEY 발급(https://apihub.kma.go.kr/) 

## application.properties 설정
![image](https://github.com/VerifiedIdiot/weather_function/assets/107241795/03e4ce5e-535d-48ff-85d0-8536f8541035)

## 프로젝트 구조
 - 프로젝트 구조는 과거 사이드프로젝트에서 모듈화만 진행한 legacy, 마이그레이션과 리팩토링이 진행된 refactored로 구분이된다.
![image](https://github.com/VerifiedIdiot/weather_function/assets/107241795/f11adb04-4c73-4b7f-8a57-49882924dd71)
 
## 변경 사항
- Java 11 -> Java 17
- Spring Boot 2.5.5 -> Spring Boot 3.2.5
- RestTemplate -> RestClient -> WebClient
- springfox-swagger2:2.9.2 -> springdoc-openapi-starter-webmvc-ui:2.0.2
- Async방식의 API 요청
- 캐싱추가

## 개선 사항
- Swagger 2.0에서 springdoc-openapi로의 업그레이드로 OpenAPI Specification 3.0 및 Spring 통합을 개선하여 API 문서 생성 및 관리를 향상 
- 스프링부트 3.x.x 부터 더이성 지원이 중단된 RestTemplate 대신 RestClient사용 및 코드간소화 -> 비동기방식인 WebClient로 대체
- 로직이 실행되는동안 API 응답 지연으로 인해 대부분의 시간이 할애됨, 그래서 비동기 요청처리 @Async를 통해 쓰레드를 조절하여 시간을 단축
- 데이터 insert 시 flush & clear 사용 대신 saveAll로 일괄처리
- 일주일 날씨정보를 프론트에서 요청시에 여전히 동일한 날씨정보인 경우 캐싱을 통해 반복된 DB조회 방지

# 결과 

# 변경전 
**일주일 날씨정보를 얻기위해 동기적으로 처리한경우 api응답이 소요된시간은 다음과 같다**
![image](https://github.com/VerifiedIdiot/weather_function/assets/107241795/ea2ac179-b2f3-48ef-a345-d0da769c2b82){: width="100px" height="100px"}
</br>
**DB에서 일주일에 해당하는 날씨정보를 프론트에서 응답받는데 소요된 시간은 다음과 같다**
![image](https://github.com/VerifiedIdiot/weather_function/assets/107241795/b11f6880-c76d-40b8-8bc9-ac7479dc3a46){: width="100px" height="100px"}



## 변경후 
**일주일 날씨정보를 얻기위해 비동기적으로 처리한경우 api응답이 소요된시간은 다음과 같다**
![image](https://github.com/VerifiedIdiot/weather_function/assets/107241795/a271c35f-eeb1-47d8-87c3-d56017cf0ee8){: width="100px" height="100px"}
</br>
**서버에서 미리 캐싱된 일주일에 해당하는 날씨정보를 프론트에서 응답받는데 소요된 시간은 다음과 같다**
![image](https://github.com/VerifiedIdiot/weather_function/assets/107241795/a409c84f-210a-4adc-9ede-1878f56059b9){: width="100px" height="100px"}

## 성능변화
 **기상청 API 송수신에 소요된 시간은 약 28.7% 감소함**
 **날씨 정보를 VIEW로 응답하는데 소요된 시간은 58.44% 감소함**



