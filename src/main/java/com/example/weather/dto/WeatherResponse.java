package com.example.weather.dto;

import java.util.List;

import lombok.Data;

@Data
public class WeatherResponse {

    private Main main;
    private List<Weather> weather;

    @Data
    public static class Main{
        private double temp;
    }

    @Data
    public static class Weather{
        private String main;
    }

}
