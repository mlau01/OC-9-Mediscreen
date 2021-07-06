package com.mediscreen.mediscreendiabetesia;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediscreen.mediscreendiabetesia.proxy.Patient;
import com.mediscreen.mediscreendiabetesia.proxy.PatientProxy;
import com.mediscreen.mediscreendiabetesia.service.DiabetesService;

import feign.Feign;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.jackson.JacksonDecoder;

@SpringBootTest
public class DiabetesServiceIT {
	
	@Autowired
	private DiabetesService diabetesService;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Test
	//@Disabled
	public void addDemoPatients(@Value("${patientapi.socket}") String patientApiSocket){
		
		List<Patient> patientList = new ArrayList<Patient>();
		patientList.add(new Patient("Lucas", "Fergusen", LocalDate.of(1968, 6, 22), "M", "387-866-1399", "2 Warren Street", null));
		patientList.add(new Patient("Pippa", "Rees", LocalDate.of(1952, 9, 27), "F", "628-423-0993", "745 West Valley Farms Drive", null));
		patientList.add(new Patient("Edward", "Arnold", LocalDate.of(1952, 11, 11), "M", "123-727-2779", "599 East Garden Ave", null));
		patientList.add(new Patient("Anthony", "Sharp", LocalDate.of(1946, 11, 26), "M", "451-761-8383", "894 Hall Street", null));
		patientList.add(new Patient("Wendy", "Ince", LocalDate.of(1958, 6, 29), "F", "802-911-9975", "4 Southampton Road", null));
		patientList.add(new Patient("Tracey", "Ross", LocalDate.of(1949, 12, 7), "F", "131-396-5049", "40 Sulphur Springs Dr", null));
		patientList.add(new Patient("Claire", "Wilson", LocalDate.of(1949, 12, 7), "F", "131-396-5049", "40 Sulphur Springs Dr", null));
		patientList.add(new Patient("Max", "Buckland", LocalDate.of(1945, 6, 24), "M", "833-534-0864", "193 Vale St", null));
		patientList.add(new Patient("Natalie", "Clark", LocalDate.of(1964, 6, 18), "F", "241-467-9197", "12 Beechwood Road", null));
		patientList.add(new Patient("Piers", "Bailey", LocalDate.of(1959, 6, 22), "M", "747-815-0557", "1202 Bumble Dr", null));
		
		PatientProxy patientProxy = Feign.builder()
				.decoder(new JacksonDecoder())
				.target(PatientProxy.class, patientApiSocket);
				
		patientList.forEach((p) -> { 
				try {
					patientProxy.addPatient(objectMapper.writeValueAsString(p));
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}

		});
		
	}
	
	@Test
	public void getDiabetesRiskTest_shouldReturnCorrectRiskLevel() {
		//Prepare

		
		
		
		
	}
	
	/*

	 */

}
