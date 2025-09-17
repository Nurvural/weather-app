package com.example.weather_app.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.example.weather_app.dto.WeatherDto;
import com.example.weather_app.producer.WeatherProducer;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class WeatherConsumer {

	// RabbitListener ile bu metod RabbitMQ'daki belirli bir queue'yu dinler
	// queues → dinlenecek queue'nun adı ("weather.queue")
	// containerFactory → hangi listener container fabrikasının kullanılacağını belirtir
	@RabbitListener(queues = "weather.queue", containerFactory = "rabbitListenerContainerFactory")
	public void receiveWeather(WeatherDto weatherDto) {
		// Mesajın içeriği, WeatherDto nesnesi olarak gelir
		 log.info("Gelen hava durumu mesajı: {}", weatherDto);
	}
}
