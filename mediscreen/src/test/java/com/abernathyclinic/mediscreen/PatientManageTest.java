package com.abernathyclinic.mediscreen;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.abernathyclinic.mediscreen.model.Patient;
import com.abernathyclinic.mediscreen.service.PatientManageService;

@SpringBootTest
public class PatientManageTest {
	
	@Autowired
	private PatientManageService patientManageService;
	
	@Test
	public void createNewPatientTest_shouldCreatePatientCorrectly() {
		
		String firstName = "John";
		String lastName = "Do";
		Date birthday = new Date(2000, 12, 05);
		String phone = "555-5534";
		char sex = 'm';
		String address = "524 Ch Rastine";
		String city = "Antibes";
		
		Patient patient = new Patient();
		patient.setFirstName(firstName);
		patient.setLastName(lastName);
		patient.setBirthday(birthday);
		patient.setPhone(phone);
		patient.setSex(sex);
		patient.setAddress(address);
		patient.setCity(city);
		
		Patient createdPatient = patientManageService.create(patient);
		
		assertEquals(firstName, createdPatient.getFirstName());
		assertEquals(lastName, createdPatient.getLastName());
		assertEquals(birthday, createdPatient.getBirthday());
		assertEquals(phone, createdPatient.getPhone());
		assertEquals(sex, createdPatient.getSex());
		assertEquals(address, createdPatient.getAddress());
		assertEquals(city, createdPatient.getCity());
	}

}
