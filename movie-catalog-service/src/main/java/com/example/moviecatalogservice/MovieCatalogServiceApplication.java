package com.example.moviecatalogservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class MovieCatalogServiceApplication {
	@Bean
	public RestTemplate getRestTemplate() {
		//Bean -> create single instance (singleton) of any object and have it shared across multiple other classes.
		return new RestTemplate(); //  https://stackoverflow.com/questions/4721279/please-explain-resttemplate
	}

	public static void main(String[] args) {
		SpringApplication.run(MovieCatalogServiceApplication.class, args);
	}

}
