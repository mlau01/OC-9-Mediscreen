package com.abernathyclinic.mediscreen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.abernathyclinic.mediscreen.model.Patient;
import com.abernathyclinic.mediscreen.service.PatientService;

@SpringBootTest
public class PatientManageTest {
	
	@Autowired
	private PatientService patientManageService;
	
	@Test
	public void createNewPatientTest_shouldCreatePatientCorrectly() {
		
		String firstName = "John";
		String lastName = "Do";
		Date dateOfBirth = new Date(2000, 12, 05);
		String phone = "555-5534";
		char sex = 'm';
		String address = "524 Ch Rastine";
		String city = "Antibes";
		
		Patient patient = new Patient();
		patient.setFirstName(firstName);
		patient.setLastName(lastName);
		patient.setDateOfBirth(dateOfBirth);
		patient.setPhone(phone);
		patient.setSex(sex);
		patient.setAddress(address);
		patient.setCity(city);
		
		Patient createdPatient = patientManageService.create(patient);
		
		assertNotNull(createdPatient.getId());
		assertEquals(firstName, createdPatient.getFirstName());
		assertEquals(lastName, createdPatient.getLastName());
		assertEquals(dateOfBirth, createdPatient.getDateOfBirth());
		assertEquals(phone, createdPatient.getPhone());
		assertEquals(sex, createdPatient.getSex());
		assertEquals(address, createdPatient.getAddress());
		assertEquals(city, createdPatient.getCity());
	}

}
