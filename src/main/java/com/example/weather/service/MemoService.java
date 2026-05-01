package com.example.weather.service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.weather.domain.Memo;
import com.example.weather.dto.WeatherResponse;
import com.example.weather.form.MemoCreateForm;
import com.example.weather.repository.MemoRepository;

@Service
public class MemoService {

    private final MemoRepository memoRepository;

    private final WeatherService weatherService;

    public MemoService(MemoRepository memoRepository, WeatherService weatherService) {
        this.memoRepository = memoRepository;
        this.weatherService = weatherService;
    }

    public List<Memo> findAllMemo() {
        return memoRepository.getMemoList();
    }

    public List<Memo> getMemoListByUserId(int userId){
        return memoRepository.getMemoListByUserId(userId);
    }

    @Transactional
    public void create(MemoCreateForm createForm, int userId) {
        WeatherResponse weatherResponse = weatherService.returnWeather(createForm.getCity());
        Memo memo = new Memo();
        memo.setTemperatureSnapshot(weatherResponse.getMain().getTemp());
        memo.setWeatherSnapshot(weatherResponse.getWeather().get(0).getMain());
        memo.setTexts(createForm.getTexts());
        memo.setCity(createForm.getCity());
        memo.setDates(Date.valueOf(createForm.getDates()));
        memo.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        memo.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        memo.setUserId(userId);

        memoRepository.create(memo);
    }

    public Memo findById(int id){
        return memoRepository.findById(id);
    }

    @Transactional
    public void update(int id, MemoCreateForm createForm){
        WeatherResponse weatherResponse = weatherService.returnWeather(createForm.getCity());
        Memo memo = findById(id);
        memo.setDates(Date.valueOf(createForm.getDates()));
        memo.setTexts(createForm.getTexts());
        memo.setCity(createForm.getCity());
        memo.setTemperatureSnapshot(weatherResponse.getMain().getTemp());
        memo.setWeatherSnapshot(weatherResponse.getWeather().get(0).getMain());
        memo.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

        memoRepository.update(memo);
    }

    @Transactional
    public void deleteById(int id){
        memoRepository.deleteById(id);
    }

}
