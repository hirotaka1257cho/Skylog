package com.example.weather.form;

import java.time.LocalDate;

import lombok.Data;

@Data
public class MemoSearchForm {

    private String texts;

    private String city;

    private LocalDate dateTo;

    private LocalDate dateFrom;

    public boolean isEmpty(){
        return (texts == null || texts.isEmpty())
        && (city == null || city.isEmpty())
        && dateTo == null
        && dateFrom == null;
    }

}
