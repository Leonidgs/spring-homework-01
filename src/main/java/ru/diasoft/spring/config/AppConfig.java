package ru.diasoft.spring.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("ru.diasoft.spring")
@PropertySource("classpath:application.properties")
public class AppConfig {
}
