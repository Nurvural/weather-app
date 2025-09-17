package com.example.weather_app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeatherDto {

    private String city;           // name
    private String country;        // sys.country
    private double temp;           // main.temp
    private double feelsLike;      // main.feels_like
    private double tempMin;        // main.temp_min
    private double tempMax;        // main.temp_max
    private String main;           // weather[0].main
    private String description;    // weather[0].description
    private int humidity;          // main.humidity
    private int pressure;          // main.pressure
    private double windSpeed;      // wind.speed
    private int windDeg;           // wind.deg
    private int cloudiness;        // clouds.all
}
