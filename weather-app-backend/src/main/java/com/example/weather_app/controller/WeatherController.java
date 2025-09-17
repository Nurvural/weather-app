package com.example.weather_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.weather_app.service.WeatherService;

@RestController
@RequestMapping("/api/weather")
@RequiredArgsConstructor
public class WeatherController {

	private final WeatherService weatherService;
	
	@PostMapping("/publish")
	public ResponseEntity<String> publishWeather(@RequestParam String city) {
		try {
			weatherService.publishWeather(city);
			return ResponseEntity.ok("Hava durumu mesajı gönderildi: " + city);
		} catch (Exception e) {
			return ResponseEntity.status(500).body("Mesaj gönderilemedi: " + e.getMessage());
		}
	}
}
