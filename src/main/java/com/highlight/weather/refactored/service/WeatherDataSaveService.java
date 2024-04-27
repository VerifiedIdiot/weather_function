package com.highlight.weather.refactored.service;

import com.highlight.weather.refactored.dto.WeatherDto;
import com.highlight.weather.refactored.entity.Weather;
import com.highlight.weather.refactored.repository.WeatherRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Log4j2
@Async
@Service("refactoredWeatherDataSaveService")
public class WeatherDataSaveService {

    @Autowired
    private WeatherRepository weatherRepository;

    public WeatherDataSaveService() {
    }


    public void deleteAllWeatherData() {
        weatherRepository.deleteAll();
    }

    @Transactional
    public void saveWeatherData(Map<String, List<List<String>>> weatherData) {
        try {
            System.out.println("단기 + 중기 예보 insert 시작");
            List<Weather> weathers = new ArrayList<>();
            for (Map.Entry<String, List<List<String>>> entry : weatherData.entrySet()) {
                for (List<String> data : entry.getValue()) {
                    WeatherDto weatherDto = new WeatherDto();
                    // 데이터 추출 및 DTO 설정
                    weatherDto.setRegion(entry.getKey());
                    weatherDto.setWeatherDate(Integer.parseInt(data.get(0)));
                    weatherDto.setMorningTemperature(Integer.parseInt(data.get(1)));
                    weatherDto.setMorningRainPercent(Integer.parseInt(data.get(2)));
                    weatherDto.setMorningWeatherCondition(data.get(3));
                    weatherDto.setAfternoonTemperature(Integer.parseInt(data.get(4)));
                    weatherDto.setAfternoonRainPercent(Integer.parseInt(data.get(5)));
                    weatherDto.setAfternoonWeatherCondition(data.get(6));

                    Weather weather = weatherDto.toEntity();
                    weathers.add(weather);
                }
            }
            weatherRepository.saveAll(weathers);
            System.out.println("단기 + 중기 예보 insert 성공");
        } catch (Exception e) {
            System.err.println("Error saving weather data: " + e.getMessage());
        }
    }
}
