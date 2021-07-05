package com.mediscreen.mediscreendiabetesia;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mediscreen.mediscreendiabetesia.proxy.Patient;
import com.mediscreen.mediscreendiabetesia.service.DiabetesService;

@SpringBootTest
public class DiabetesServiceIT {
	
	@Autowired
	private DiabetesService diabetesService;
	
	@Test
	public void getDiabetesRiskTest_shouldReturnCorrectRiskLevel() {
		//Prepare
		List<Patient> patientList = new ArrayList<Patient>();
		patientList.add(new Patient(LocalDate.of(1968, 6, 22), "M"));
		patientList.add(new Patient(LocalDate.of(1952, 8, 27), "F"));
		patientList.add(new Patient(LocalDate.of(1952, 11, 11), "M"));
		patientList.add(new Patient(LocalDate.of(1946, 11, 262), "M"));
		patientList.add(new Patient(LocalDate.of(1958, 6, 29), "F"));
		patientList.add(new Patient(LocalDate.of(1949, 12, 7), "F"));
		patientList.add(new Patient(LocalDate.of(1966, 12, 31), "F"));
		patientList.add(new Patient(LocalDate.of(1945, 6, 24), "M"));
		patientList.add(new Patient(LocalDate.of(1964, 6, 18), "F"));
		patientList.add(new Patient(LocalDate.of(1959, 6, 28), "M"));
		
		
		
		
	}
	
	/*


	 */

}
