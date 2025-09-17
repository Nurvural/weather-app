package com.example.weather_app.mapper;

import com.example.weather_app.dto.WeatherDto;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Component;

@Component
public class WeatherMapper {
                            
    public WeatherDto map(JsonNode jsonNode) {  //Jackson kütüphanesi sınıfı
        return WeatherDto.builder()
        		   .city(jsonNode.path("name").asText())
                   .country(jsonNode.path("sys").path("country").asText())
                   .temp(jsonNode.path("main").path("temp").asDouble())
                   .feelsLike(jsonNode.path("main").path("feels_like").asDouble())
                   .tempMin(jsonNode.path("main").path("temp_min").asDouble())
                   .tempMax(jsonNode.path("main").path("temp_max").asDouble())
                   .humidity(jsonNode.path("main").path("humidity").asInt())
                   .pressure(jsonNode.path("main").path("pressure").asInt())
                   .main(jsonNode.path("weather").get(0).path("main").asText())
                   .description(jsonNode.path("weather").get(0).path("description").asText())
                   .windSpeed(jsonNode.path("wind").path("speed").asDouble())
                   .windDeg(jsonNode.path("wind").path("deg").asInt())
                   .cloudiness(jsonNode.path("clouds").path("all").asInt())
                .build();
    }
}