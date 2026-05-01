package com.example.weather.form;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class MemoCreateForm {

    @NotNull(message = "日付を入力してください")
    private LocalDate dates;

    @NotBlank(message = "本文を入力してください")
    private String texts;

    @NotBlank(message = "都市名を入力してください")
    private String city;

    public LocalDate getDates() {
        return dates;
    }

    public void setDates(LocalDate dates) {
        this.dates = dates;
    }

    public String getTexts() {
        return texts;
    }

    public void setTexts(String texts) {
        this.texts = texts;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "MemoCreateForm [dates=" + dates + ", texts=" + texts + ", city=" + city + "]";
    }

}
