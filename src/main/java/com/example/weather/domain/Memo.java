package com.example.weather.domain;

import java.sql.Date;
import java.sql.Timestamp;

public class Memo {
    
    private int id;
    private Date dates;
    private String texts;
    private String weatherSnapshot;
    private Double temperatureSnapshot;
    private int userId;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String city;
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Date getDates() {
        return dates;
    }
    public void setDates(Date dates) {
        this.dates = dates;
    }
    public String getTexts() {
        return texts;
    }
    public void setTexts(String texts) {
        this.texts = texts;
    }
    public String getWeatherSnapshot() {
        return weatherSnapshot;
    }
    public void setWeatherSnapshot(String weatherSnapshot) {
        this.weatherSnapshot = weatherSnapshot;
    }
    public Double getTemperatureSnapshot() {
        return temperatureSnapshot;
    }
    public void setTemperatureSnapshot(Double temperatureSnapshot) {
        this.temperatureSnapshot = temperatureSnapshot;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public Timestamp getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
    public Timestamp getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    @Override
    public String toString() {
        return "Memo [id=" + id + ", dates=" + dates + ", texts=" + texts + ", weatherSnapshot=" + weatherSnapshot
                + ", temperatureSnapshot=" + temperatureSnapshot + ", userId=" + userId + ", createdAt=" + createdAt
                + ", updatedAt=" + updatedAt + ", city=" + city + "]";
    }

    
}
