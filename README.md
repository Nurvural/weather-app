# Hava Durumu Uygulaması (Weather App)
Spring Boot ile geliştirilmiş bir hava durumu uygulamasıdır. Uygulama, OpenWeatherMap API’sinden hava durumu verilerini çekerek RabbitMQ üzerinden mesaj gönderimi yapar. Ayrıca uygulama, RabbitMQ üzerinden gönderilen hava durumu mesajlarını alan bir consumer ile birlikte çalışır.

## Özellikler

- 🌤️ OpenWeatherMap API üzerinden şehir bazlı hava durumu verilerini alma
- 📩 RabbitMQ ile mesajlaşma ve servisler arası iletişim
- 🔄 JSON verilerini `WeatherDto` nesnesine map etme
- 📝 SLF4J / Logback ile loglama
- ✅ Unit testler (JUnit 5 & Mockito)
- 💻 React + Tailwind ile responsive ve modern arayüz

## Teknoloji Yığını

### Backend & Core
- Java 21
- Spring Boot
- RabbitMQ
- SLF4J / Logback
- JUnit 5 & Mockito
- Lombok

### Frontend
- React
- Tailwind CSS


