package com.ipstresser.app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:machine-config.properties")
@PropertySource("classpath:mail-config.properties")
public class PropertySourceConfiguration {
}
