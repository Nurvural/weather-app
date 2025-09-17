package com.example.weather_app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.example.weather_app.producer.WeatherProducer;

@Service
@RequiredArgsConstructor
public class WeatherService {

    private final WeatherProducer weatherProducer;

    public void publishWeather(String city) {
        weatherProducer.sendWeather(city);
    }
}
