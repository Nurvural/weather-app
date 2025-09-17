package com.example.weather_app;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.example.weather_app.dto.WeatherDto;
import com.example.weather_app.mapper.WeatherMapper;

class WeatherMapperTest {

	private final WeatherMapper weatherMapper = new WeatherMapper();
	private final ObjectMapper objectMapper = new ObjectMapper();

	@Test
	void testMap() throws Exception {
		// Örnek JSON
		String json = """
				{
				  "name": "London",
				  "sys": {"country": "GB"},
				  "main": {"temp": 18.34, "feels_like": 18.48, "temp_min": 17.79, "temp_max": 18.96, "humidity": 86, "pressure": 1016},
				  "weather": [{"main": "Clouds", "description": "overcast clouds"}],
				  "wind": {"speed": 6.69, "deg": 230},
				  "clouds": {"all": 100}
				}
				""";

		JsonNode jsonNode = objectMapper.readTree(json);

		WeatherDto dto = weatherMapper.map(jsonNode);

		assertEquals("London", dto.getCity());
		assertEquals("GB", dto.getCountry());
		assertEquals(18.34, dto.getTemp());
		assertEquals(18.48, dto.getFeelsLike());
		assertEquals(17.79, dto.getTempMin());
		assertEquals(18.96, dto.getTempMax());
		assertEquals(86, dto.getHumidity());
		assertEquals(1016, dto.getPressure());
		assertEquals("Clouds", dto.getMain());
		assertEquals("overcast clouds", dto.getDescription());
		assertEquals(6.69, dto.getWindSpeed());
		assertEquals(230, dto.getWindDeg());
		assertEquals(100, dto.getCloudiness());
	}
}