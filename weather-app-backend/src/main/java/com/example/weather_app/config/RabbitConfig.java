package com.example.weather_app.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration 
public class RabbitConfig {

    // RabbitMQ'da mesajların tutulacağı kuyruk
    @Bean
    public Queue weatherQueue() {
        // "weather.queue" adıyla durable (kalıcı) bir kuyruk oluşturuyoruz
        return new Queue("weather.queue", true);
    }

    // Mesajları yönlendirmek için kullanılan exchange
    @Bean
    public DirectExchange weatherExchange() {
        // DirectExchange kullanıyoruz; mesajlar routing key ile tam eşleşen queue'ya gider
        return new DirectExchange("weather.exchange");
    }

    // Exchange ile Queue'yu bağlayan binding
    @Bean
    public Binding binding(Queue weatherQueue, DirectExchange weatherExchange) {
        // "weather.key" ile gelen mesajlar weatherQueue'ya yönlendirilir
        return BindingBuilder.bind(weatherQueue).to(weatherExchange).with("weather.key");
    }

    // @RabbitListener metodları için listener container factory
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        // Gelen JSON mesajların Java nesnelerine (WeatherDto) çevrilmesini sağlıyoruz
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        return factory;
    }

}
