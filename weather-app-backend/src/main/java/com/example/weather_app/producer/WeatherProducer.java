package com.example.weather_app.producer;

import com.example.weather_app.dto.WeatherDto;
import com.example.weather_app.mapper.WeatherMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
@Slf4j
public class WeatherProducer {

    // RabbitMQ ile mesaj gönderme işlemi için kullanılır
    private final AmqpTemplate amqpTemplate;

    // JSON verisini WeatherDto nesnesine dönüştürmek için kullanılır
    private final WeatherMapper weatherMapper;

    // HTTP isteği yapmak için kullanılır, OpenWeatherMap API’sine bu ile istek atılır
    private final RestTemplate restTemplate;

    // JSON verisini Java nesnelerine çevirmek için Jackson ObjectMapper
    private final ObjectMapper objectMapper;

    // RabbitMQ exchange adı (application.properties'den alınıyor)
    @Value("${weather.exchange}")
    private String exchange;

    // RabbitMQ routing key (application.properties'den alınıyor)
    @Value("${weather.routingKey}")
    private String routingKey;

    // OpenWeatherMap API URL’i (application.properties'den alınıyor)
    @Value("${openweathermap.api.url}")
    private String apiUrl;

    // OpenWeatherMap API anahtarı (application.properties'den alınıyor)
    @Value("${openweathermap.api.key}")
    private String apiKey;

    // Belirli bir şehir için hava durumu verisini çekip RabbitMQ'ya gönderir
    public void sendWeather(String city) {
        try {
            // API isteği URL'si hazırlanıyor
            String url = String.format("%s?q=%s&appid=%s&units=metric", apiUrl, city, apiKey);

            // OpenWeatherMap API’den JSON formatında veri çekiliyor
            String response = restTemplate.getForObject(url, String.class);

            // JSON String’i JsonNode nesnesine dönüştürülüyor
            JsonNode jsonNode = objectMapper.readTree(response);

            // JsonNode içerisindeki veriler WeatherDto nesnesine map ediliyor
            WeatherDto dto = weatherMapper.map(jsonNode);

            // WeatherDto nesnesi RabbitMQ’ya gönderiliyor
            // exchange → mesajın gideceği exchange
            // routingKey → hangi queue’ye yönlendirileceği
            // dto → gönderilecek veri
            amqpTemplate.convertAndSend(exchange, routingKey, dto);
            log.info("Gönderilen hava durumu mesajı: {}", dto);
        } catch (Exception e) {
            log.error("Hava durumu gönderilemedi", e);
        }
    }
}
