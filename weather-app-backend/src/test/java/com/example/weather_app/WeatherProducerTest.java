package com.example.weather_app;

import com.example.weather_app.dto.WeatherDto;
import com.example.weather_app.mapper.WeatherMapper;
import com.example.weather_app.producer.WeatherProducer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.web.client.RestTemplate;
import java.lang.reflect.Field;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class WeatherProducerTest {

	@Test
	void testSendWeather() throws Exception {
		// 1. Mock objeleri oluştur
		AmqpTemplate amqpTemplate = Mockito.mock(AmqpTemplate.class);
		WeatherMapper weatherMapper = Mockito.mock(WeatherMapper.class);
		RestTemplate restTemplate = Mockito.mock(RestTemplate.class);
		ObjectMapper objectMapper = new ObjectMapper();

		// 2. Producer'ı test constructor ile oluştur (Spring inject yok)
		WeatherProducer producer = new WeatherProducer(amqpTemplate, weatherMapper, restTemplate, objectMapper);

		Field exchangeField = WeatherProducer.class.getDeclaredField("exchange");
		exchangeField.setAccessible(true);
		exchangeField.set(producer, "weather.exchange");

		Field routingKeyField = WeatherProducer.class.getDeclaredField("routingKey");
		routingKeyField.setAccessible(true);
		routingKeyField.set(producer, "weather.key");

		Field apiUrlField = WeatherProducer.class.getDeclaredField("apiUrl");
		apiUrlField.setAccessible(true);
		apiUrlField.set(producer, "http://api.openweathermap.org/data/2.5/weather");

		Field apiKeyField = WeatherProducer.class.getDeclaredField("apiKey");
		apiKeyField.setAccessible(true);
		apiKeyField.set(producer, "fakeApiKey123");

		// 3. Örnek JSON ve DTO
		String json = "{ \"name\": \"London\", \"sys\": {\"country\": \"GB\"}, \"main\": {\"temp\": 18.34, \"feels_like\": 18.48, \"temp_min\": 17.79, \"temp_max\": 18.96, \"humidity\": 86, \"pressure\": 1016}, \"weather\": [{\"main\": \"Clouds\", \"description\": \"overcast clouds\"}], \"wind\": {\"speed\": 6.69, \"deg\": 230}, \"clouds\": {\"all\": 100} }";
		JsonNode jsonNode = objectMapper.readTree(json);

		WeatherDto dto = WeatherDto.builder().city("London").country("GB").temp(18.34).feelsLike(18.48).tempMin(17.79)
				.tempMax(18.96).humidity(86).pressure(1016).main("Clouds").description("overcast clouds")
				.windSpeed(6.69).windDeg(230).cloudiness(100).build();

		// 4. Mapper ve RestTemplate davranışlarını tanımla
		when(restTemplate.getForObject(anyString(), eq(String.class))).thenReturn(json);
		when(weatherMapper.map(any(JsonNode.class))).thenReturn(dto);

		// 5. Metodu test et
		producer.sendWeather("London");

		// 6. RabbitMQ convertAndSend çağrıldığını doğrula
		verify(amqpTemplate, times(1)).convertAndSend("weather.exchange", "weather.key", dto);
	}
}
