package com.example.weather.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.weather.domain.Memo;
import com.example.weather.dto.WeatherResponse;
import com.example.weather.form.MemoCreateForm;
import com.example.weather.repository.MemoRepository;

@ExtendWith(MockitoExtension.class)
public class MemoServiceTest {
    @Mock
    private MemoRepository memoRepository;
    @Mock
    private WeatherService weatherService;
    @InjectMocks
    private MemoService memoService;

    @Test
    @DisplayName("findByIdのテスト")
    void findById(){
        Memo memo = new Memo();
        memo.setId(1);
        memo.setTexts("テスト");

        when(memoRepository.findById(anyInt())).thenReturn(memo);

        Memo memo2 = memoService.findById(1);
        assertEquals(1, memo2.getId());
        assertEquals("テスト", memo2.getTexts());
    }

    @Test
    @DisplayName("createのテスト")
    void create(){
        MemoCreateForm createForm = new MemoCreateForm();
        createForm.setCity("kanagawa");
        createForm.setDates(LocalDate.of(2026, 4, 17));

        WeatherResponse weatherResponse = new WeatherResponse();
        WeatherResponse.Main main = new WeatherResponse.Main();
        main.setTemp(20.0);
        WeatherResponse.Weather weather = new WeatherResponse.Weather();
        weather.setMain("sunny");
        weatherResponse.setMain(main);
        weatherResponse.setWeather(List.of(weather));

        when(weatherService.returnWeather(anyString())).thenReturn(weatherResponse);
        memoService.create(createForm, 1);
        verify(memoRepository).create(any(Memo.class));
    }
}
