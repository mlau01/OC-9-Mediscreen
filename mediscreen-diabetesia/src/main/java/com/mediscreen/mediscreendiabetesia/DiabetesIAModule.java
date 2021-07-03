package com.mediscreen.mediscreendiabetesia;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mediscreen.mediscreendiabetesia.proxy.NoteProxy;
import com.mediscreen.mediscreendiabetesia.proxy.PatientProxy;

import feign.Feign;
import feign.gson.GsonDecoder;

@Configuration
public class DiabetesIAModule {
	
	
	
	@Bean
	public NoteProxy getProxyNote(@Value("${noteapi.socket}") String noteApiSocket) {
		return Feign.builder().decoder(new GsonDecoder()).target(NoteProxy.class, noteApiSocket);
	}
	
	@Bean
	public PatientProxy getProxyPatient(@Value("${patientapi.socket}") String patientApiSocket) {
		return Feign.builder().decoder(new GsonDecoder()).target(PatientProxy.class, patientApiSocket);
	}

}
