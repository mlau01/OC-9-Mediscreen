package com.mediscreen.mediscreendiabetesia;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediscreen.mediscreendiabetesia.service.DiabetesService;

import feign.Feign;
import feign.Headers;
import feign.RequestLine;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import lombok.AllArgsConstructor;
import lombok.Data;

@SpringBootTest
public class DiabetesServiceIT {
	
	@AllArgsConstructor
	@Data
	class InnerPatient {
		 private String firstName;
		 private String lastName;
		 private String dateOfBirth;
		 private String sex;
		 private String phone;
		 private String address;
	}
	
	interface InnerPatientProxy {
		@RequestLine("POST /patients")
		@Headers("Content-Type: application/json")
		InnerPatient addPatient(InnerPatient patient);
	}
	
	@Autowired
	private DiabetesService diabetesService;
	
	@Test
	//@Disabled
	public void addDemoPatients(@Value("${patientapi.socket}") String patientApiSocket, @Autowired ObjectMapper objectMapper){
		
		List<InnerPatient> patientList = new ArrayList<InnerPatient>();
		patientList.add(new InnerPatient("Lucas", "Fergusen", "1968-06-22", "M", "387-866-1399", "2 Warren Street"));
		patientList.add(new InnerPatient("Pippa", "Rees", "1952-09-27", "F", "628-423-0993", "745 West Valley Farms Drive"));
		patientList.add(new InnerPatient("Edward", "Arnold", "1952-11-11", "M", "123-727-2779", "599 East Garden Ave"));
		patientList.add(new InnerPatient("Anthony", "Sharp", "1946-11-26", "M", "451-761-8383", "894 Hall Street"));
		patientList.add(new InnerPatient("Wendy", "Ince", "1958-06-29", "F", "802-911-9975", "4 Southampton Road"));
		patientList.add(new InnerPatient("Tracey", "Ross", "1949-12-07", "F", "131-396-5049", "40 Sulphur Springs Dr"));
		patientList.add(new InnerPatient("Claire", "Wilson", "1949-12-07", "F", "131-396-5049", "40 Sulphur Springs Dr"));
		patientList.add(new InnerPatient("Max", "Buckland", "1945-06-24", "M", "833-534-0864", "193 Vale St"));
		patientList.add(new InnerPatient("Natalie", "Clark", "1964-06-18", "F", "241-467-9197", "12 Beechwood Road"));
		patientList.add(new InnerPatient("Piers", "Bailey", "1959-06-28", "M", "747-815-0557", "1202 Bumble Dr "));
		
		InnerPatientProxy patientProxy = Feign.builder()
				.decoder(new GsonDecoder())
				.encoder(new GsonEncoder())
				.target(InnerPatientProxy.class, patientApiSocket);
		
		patientList.forEach((p) -> { 
				patientProxy.addPatient(p);

		});
		
	}
	@Test
	public void getDiabetesRiskTest_shouldReturnCorrectRiskLevel() {
		//Prepare

		
		
		
		
	}
	
	/*

	 */

}
