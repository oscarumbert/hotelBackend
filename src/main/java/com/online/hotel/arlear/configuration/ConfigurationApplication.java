package com.online.hotel.arlear.configuration;


import org.modelmapper.ModelMapper;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.Data;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
@Data
public class ConfigurationApplication {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
		
		
	}
	
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {

			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/reservation/**")
					.allowedOrigins("http://localhost:8090","https://online-hotel-frontend.herokuapp.com/")
					.allowedMethods("GET", "POST", "PUT", "DELETE")
					.maxAge(3600);
				registry.addMapping("/user/**")
					.allowedOrigins("http://localhost:8090","https://online-hotel-frontend.herokuapp.com/")
					.allowedMethods("GET", "POST", "PUT", "DELETE")
					.maxAge(3600);
				registry.addMapping("/product/**")
				.allowedOrigins("http://localhost:8090","https://online-hotel-frontend.herokuapp.com/")
				.allowedMethods("GET", "POST", "PUT", "DELETE")
				.maxAge(3600);
			}
			
			
		};
	}
}
