package com.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
@ComponentScan
@SpringBootApplication
public class SpringBootRedisApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootRedisApplication.class, args);
	}
}