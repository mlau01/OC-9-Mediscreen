package com.mediscreen.mediscreendiabetesia.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class JacksonConfig {
	
	@Bean
	@Primary
	public ObjectMapper objectMapper() {
	    JavaTimeModule module = new JavaTimeModule();
	    return new ObjectMapper()
	      .registerModule(module);
	}

}
