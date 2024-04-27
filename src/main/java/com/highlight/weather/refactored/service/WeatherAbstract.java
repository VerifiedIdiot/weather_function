package com.highlight.weather.refactored.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
@Service("refactoredWeatherAbstract")
@RequiredArgsConstructor
public abstract class WeatherAbstract {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHH");
    private final RestClient restClient;

    protected String sendGetRequest(String url, String apiKey, Map<String, String> queryParams ) {
        // 동적 URI 생성 파라미터로 전달받은 URI 정보를 바탕으로 새로운 인스턴스를 생성해서 간편하다 !!
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        queryParams.forEach(builder::queryParam);
        ResponseEntity<String> response;
        // retrieve가 4xx와 5xx를 에러로 처리해준다! try-catch가 필요없으니 코드가 짧아진다 !!!
        response = restClient.get()
                    .uri(builder.toUriString())
                    .header("authKey", apiKey)
                    .retrieve()
                    // body(String.class)를 사용하면 응답.body를 받을 수 있지만 ResponseEntity로 받아 추후에 status 별 에러
                    // 에러 핸들링을 위해 유지
                    .toEntity(String.class);
        return response.getBody();
    }

    private int formatDate(LocalDateTime dateTime) {
        return Integer.parseInt(dateTime.format(DATE_TIME_FORMATTER));
    }
    protected Map<String, Integer> shortDaysParam() {
        LocalDate today = LocalDate.now();

        LocalDateTime yesterdayNoon = LocalDateTime.of(today.minusDays(1), LocalTime.of(12, 0));
        LocalDateTime todayNoon = LocalDateTime.of(today, LocalTime.of(12, 0));

        Map<String, Integer> shortDateParams = new HashMap<>();
        shortDateParams.put("today", formatDate(yesterdayNoon));
        shortDateParams.put("2DaysAfter", formatDate(todayNoon));
//        System.out.println("오늘  : " + formatDate(yesterdayNoon) + "이틀 뒤 : " + formatDate(todayNoon));
        return shortDateParams;
    }

    protected Map<String, String> shortQueryParams(String regCode, Map<String, Integer> shortDateParams) {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("reg", regCode);
        queryParams.put("tmfc1", String.valueOf(shortDateParams.get("today")));
        queryParams.put("tmfc2", String.valueOf(shortDateParams.get("2DaysAfter")));
        queryParams.put("help", "0");

        return queryParams;
    }

    protected Map<String, Integer> middleDaysParam() {
        LocalDate now = LocalDate.now();
        Map<String, Integer> dateParams = new HashMap<>();
        dateParams.put("today", formatDate(LocalDateTime.of(now, LocalTime.MIDNIGHT)));
        dateParams.put("tomorrow", formatDate(LocalDateTime.of(now.plusDays(1), LocalTime.MIDNIGHT)));
        dateParams.put("sevenDaysAfter", formatDate(LocalDateTime.of(now.plusDays(6), LocalTime.MIDNIGHT)));

        return dateParams;
    }

    protected Map<String, String> middleQueryParams(String regCode, Map<String, Integer> dateParams) {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("reg", regCode);
        queryParams.put("tmef1", String.valueOf(dateParams.get("tomorrow")));
        queryParams.put("tmef2", String.valueOf(dateParams.get("sevenDaysAfter")));
        queryParams.put("help", "0");

        return queryParams;
    }
}
