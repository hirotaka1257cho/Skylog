package com.example.weather.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.weather.dto.WeatherResponse;


@Service
public class WeatherService {

    private final RestTemplate restTemplate;

    public WeatherService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Value("${weather.api.key}")
    private String key;
    @Value("${weather.api.url}")
    private String url;

    public WeatherResponse returnWeather(String name) {

        WeatherResponse response = restTemplate.getForObject(
                url + "?q=" + name + "&appid=" + key + "&units=metric",
                WeatherResponse.class);

                return response;
    }
}
