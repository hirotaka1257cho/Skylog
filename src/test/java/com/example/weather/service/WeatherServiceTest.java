package com.example.weather.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import com.example.weather.dto.WeatherResponse;

@ExtendWith(MockitoExtension.class)
public class WeatherServiceTest {

    @Mock
    private RestTemplate restTemplate;
    @InjectMocks
    private WeatherService weatherService;

    @Test
    @DisplayName("returnWeather()のテスト")
    void testReturnWeather(){
        WeatherResponse weatherResponse = new WeatherResponse();
        WeatherResponse.Main main = new WeatherResponse.Main();
        main.setTemp(20.0);
        WeatherResponse.Weather weather = new WeatherResponse.Weather();
        weather.setMain("sunny");
        weatherResponse.setMain(main);
        weatherResponse.setWeather(List.of(weather));

        when(restTemplate.getForObject(anyString(), eq(WeatherResponse.class))).thenReturn(weatherResponse);

        WeatherResponse response = weatherService.returnWeather("Tokyo") ;
        assertEquals(20.0, response.getMain().getTemp());
        assertEquals("sunny", response.getWeather().get(0).getMain());
    }
}
