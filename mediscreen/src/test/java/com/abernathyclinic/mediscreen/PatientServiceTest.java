package com.abernathyclinic.mediscreen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.validation.ConstraintViolationException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.abernathyclinic.mediscreen.model.Patient;
import com.abernathyclinic.mediscreen.service.PatientService;

@SpringBootTest
public class PatientServiceTest {
	
	@Autowired
	private PatientService patientService;
	
	@Test
	@Disabled
	public void createNewPatientTest_shouldCreatePatientCorrectly() {
		LocalDate dateOfBirth = LocalDate.of(1984, 8, 30);
		String firstName = "John";
		String lastName = "Do";
		String phone = "555-5534";
		String sex = "M";
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
		
		Patient createdPatient = patientService.create(patient);
		
		assertNotNull(createdPatient.getId());
		assertEquals(firstName, createdPatient.getFirstName());
		assertEquals(lastName, createdPatient.getLastName());
		assertEquals(dateOfBirth, createdPatient.getDateOfBirth());
		assertEquals(phone, createdPatient.getPhone());
		assertEquals(sex, createdPatient.getSex());
		assertEquals(address, createdPatient.getAddress());
		assertEquals(city, createdPatient.getCity());
	}
	
	@Test
	public void createPatientWithConstraintViolationTest_shouldThrowException() {
		
		//BLANK CONSTRAINT TESTS
		Patient patient1 = new Patient(null, "", "Do", LocalDate.of(1984, 8, 30), "M", "555-3456", "524 Ch Rastine", "Antibes");
		Assertions.assertThatExceptionOfType(ConstraintViolationException.class).isThrownBy( () -> patientService.create(patient1));
		
		Patient patient2 = new Patient(null, "John", "", LocalDate.of(1984, 8, 30), "M", "555-3456", "524 Ch Rastine", "Antibes");
		Assertions.assertThatExceptionOfType(ConstraintViolationException.class).isThrownBy( () -> patientService.create(patient2));
		
		Patient patient4 = new Patient(null, "John", "Do", LocalDate.of(1984, 8, 30), "M", "", "524 Ch Rastine", "Antibes");
		Assertions.assertThatExceptionOfType(ConstraintViolationException.class).isThrownBy( () -> patientService.create(patient4));
		
		Patient patient5 = new Patient(null, "John", "Do", LocalDate.of(1984, 8, 30), "M", "555-3456", "", "Antibes");
		Assertions.assertThatExceptionOfType(ConstraintViolationException.class).isThrownBy( () -> patientService.create(patient5));
		
		//NULL CONSTRAINT TEST
		Patient patient6 = new Patient(null, null, "Do", LocalDate.of(1984, 8, 30), "M", "555-3456", "524 Ch Rastine", "Antibes");
		Assertions.assertThatExceptionOfType(ConstraintViolationException.class).isThrownBy( () -> patientService.create(patient6));
		
		Patient patient7 = new Patient(null, "John", null, LocalDate.of(1984, 8, 30), "M", "555-3456", "524 Ch Rastine", "Antibes");
		Assertions.assertThatExceptionOfType(ConstraintViolationException.class).isThrownBy( () -> patientService.create(patient7));
		
		Patient patient3 = new Patient(null, "John", "Do", null, "M", "555-3456", "524 Ch Rastine", "Antibes");
		Assertions.assertThatExceptionOfType(ConstraintViolationException.class).isThrownBy( () -> patientService.create(patient3));
		
		Patient patient8 = new Patient(null, "John", "Do", LocalDate.of(1984, 8, 30), "M", null, "524 Ch Rastine", "Antibes");
		Assertions.assertThatExceptionOfType(ConstraintViolationException.class).isThrownBy( () -> patientService.create(patient8));
		
		Patient patient9 = new Patient(null, "John", "Do", LocalDate.of(1984, 8, 30), "M", "555-3456", null, "Antibes");
		Assertions.assertThatExceptionOfType(ConstraintViolationException.class).isThrownBy( () -> patientService.create(patient9));
		
		//TEMPORAL CONSTRAINT
		Patient patient10 = new Patient(null, "John", "Do", LocalDate.of(2222, 8, 30), "M", "555-3456", null, "Antibes");
		Assertions.assertThatExceptionOfType(ConstraintViolationException.class).isThrownBy( () -> patientService.create(patient10));
		
		//SEX CONSTRAINT
		Patient patient11 = new Patient(null, "John", "Do", LocalDate.of(2222, 8, 30), "", "555-3456", "524 Ch Rastine", "Antibes");
		Assertions.assertThatExceptionOfType(ConstraintViolationException.class).isThrownBy( () -> patientService.create(patient11));
		
		Patient patient12 = new Patient(null, "John", "Do", LocalDate.of(2222, 8, 30), "A", "555-3456", "524 Ch Rastine", "Antibes");
		Assertions.assertThatExceptionOfType(ConstraintViolationException.class).isThrownBy( () -> patientService.create(patient12));
		
		Patient patient13 = new Patient(null, "John", "Do", LocalDate.of(2222, 8, 30), null, "555-3456", "524 Ch Rastine", "Antibes");
		Assertions.assertThatExceptionOfType(ConstraintViolationException.class).isThrownBy( () -> patientService.create(patient13));
		
	}

}
