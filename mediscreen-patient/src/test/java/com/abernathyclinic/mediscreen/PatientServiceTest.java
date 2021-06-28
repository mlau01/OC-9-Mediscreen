package com.abernathyclinic.mediscreen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.abernathyclinic.mediscreen.exception.AlreadyExistsPatientException;
import com.abernathyclinic.mediscreen.exception.NoSuchPatientException;
import com.abernathyclinic.mediscreen.model.Patient;
import com.abernathyclinic.mediscreen.service.PatientService;

@SpringBootTest
public class PatientServiceTest {
	
	@Autowired
	private PatientService patientService;
	
	@Test
	public void listPatientsTest_shouldReturnPatientList() {
		patientService.getAllPatient().forEach(patient -> assertNotNull(patient.getId()));
		
	}
	
	@Test
	public void createNewPatientTest_shouldCreatePatientCorrectly() throws AlreadyExistsPatientException, NoSuchPatientException {
		//Set data
		LocalDate dateOfBirth = LocalDate.of(1984, 8, 30);
		String firstName = "John";
		String lastName = "Do";
		String phone = "555-5534";
		String sex = "M";
		String address = "524 Ch Rastine";
		String city = "Antibes";
		
		Patient patient = new Patient(firstName, lastName, dateOfBirth, sex, phone, address, city);

		//Create patient
		Patient createdPatient = patientService.create(patient);
		
		//Already exists assert
		assertThrows(AlreadyExistsPatientException.class, () -> patientService.create(patient));
		
		//Assert
		assertNotNull(patientService.read(createdPatient.getId()));
		assertEquals(firstName, createdPatient.getFirstName());
		assertEquals(lastName, createdPatient.getLastName());
		assertEquals(dateOfBirth, createdPatient.getDateOfBirth());
		assertEquals(phone, createdPatient.getPhone());
		assertEquals(sex, createdPatient.getSex());
		assertEquals(address, createdPatient.getAddress());
		assertEquals(city, createdPatient.getCity());
		
		//Update the patient
		LocalDate m_dateOfBirth = LocalDate.of(1914, 1, 2);
		String m_firstName = "Berth";
		String m_lastName = "Alister";
		String m_phone = "1234-12345";
		String m_sex = "F";
		String m_address = "19 Rue Cartel";
		String m_city = "Nice";
		
		createdPatient.setFirstName(m_firstName);
		createdPatient.setLastName(m_lastName);
		createdPatient.setAddress(m_address);
		createdPatient.setDateOfBirth(m_dateOfBirth);
		createdPatient.setSex(m_sex);
		createdPatient.setPhone(m_phone);
		createdPatient.setCity(m_city);
		Patient updatedPatient = patientService.update(createdPatient);
		
		//Assert
		assertEquals(createdPatient.getId(), updatedPatient.getId());
		assertEquals(m_firstName, updatedPatient.getFirstName());
		assertEquals(m_lastName, updatedPatient.getLastName());
		assertEquals(m_dateOfBirth, updatedPatient.getDateOfBirth());
		assertEquals(m_phone, updatedPatient.getPhone());
		assertEquals(m_sex, updatedPatient.getSex());
		assertEquals(m_address, updatedPatient.getAddress());
		assertEquals(m_city, updatedPatient.getCity());
		
		//Delete patient
		int saveId = createdPatient.getId();
		patientService.delete(String.valueOf(saveId));
		//Assert
		assertThrows(NoSuchPatientException.class, () -> patientService.read(saveId));
		
		
	}
	
	@Test
	public void createPatientWithConstraintViolationTest_shouldThrowException() {
		
		//BLANK CONSTRAINT TESTS
		Patient patient1 = new Patient("", "Do", LocalDate.of(1984, 8, 30), "M", "555-3456", "524 Ch Rastine", "Antibes");
		assertThrows(ConstraintViolationException.class, () -> patientService.create(patient1));
		
		Patient patient2 = new Patient("John", "", LocalDate.of(1984, 8, 30), "M", "555-3456", "524 Ch Rastine", "Antibes");
		assertThrows(ConstraintViolationException.class, () -> patientService.create(patient2));
		
		Patient patient4 = new Patient("John", "Do", LocalDate.of(1984, 8, 30), "M", "", "524 Ch Rastine", "Antibes");
		assertThrows(ConstraintViolationException.class, () -> patientService.create(patient4));
		
		Patient patient5 = new Patient("John", "Do", LocalDate.of(1984, 8, 30), "M", "555-3456", "", "Antibes");
		assertThrows(ConstraintViolationException.class, () -> patientService.create(patient5));
		
		//NULL CONSTRAINT TEST
		Patient patient6 = new Patient(null, "Do", LocalDate.of(1984, 8, 30), "M", "555-3456", "524 Ch Rastine", "Antibes");
		assertThrows(ConstraintViolationException.class, () -> patientService.create(patient6));
		
		Patient patient7 = new Patient("John", null, LocalDate.of(1984, 8, 30), "M", "555-3456", "524 Ch Rastine", "Antibes");
		assertThrows(ConstraintViolationException.class, () -> patientService.create(patient7));
		
		Patient patient3 = new Patient("John", "Do", null, "M", "555-3456", "524 Ch Rastine", "Antibes");
		assertThrows(ConstraintViolationException.class, () -> patientService.create(patient3));
		
		Patient patient8 = new Patient("John", "Do", LocalDate.of(1984, 8, 30), "M", null, "524 Ch Rastine", "Antibes");
		assertThrows(ConstraintViolationException.class, () -> patientService.create(patient8));
		
		Patient patient9 = new Patient("John", "Do", LocalDate.of(1984, 8, 30), "M", "555-3456", null, "Antibes");
		assertThrows(ConstraintViolationException.class, () -> patientService.create(patient9));
		
		//TEMPORAL CONSTRAINT
		Patient patient10 = new Patient("John", "Do", LocalDate.of(2222, 8, 30), "M", "555-3456", null, "Antibes");
		assertThrows(ConstraintViolationException.class, () -> patientService.create(patient10));
		
		//SEX CONSTRAINT
		Patient patient11 = new Patient("John", "Do", LocalDate.of(2222, 8, 30), "", "555-3456", "524 Ch Rastine", "Antibes");
		assertThrows(ConstraintViolationException.class, () -> patientService.create(patient11));
		
		Patient patient12 = new Patient("John", "Do", LocalDate.of(2222, 8, 30), "A", "555-3456", "524 Ch Rastine", "Antibes");
		assertThrows(ConstraintViolationException.class, () -> patientService.create(patient12));
		
		Patient patient13 = new Patient("John", "Do", LocalDate.of(2222, 8, 30), null, "555-3456", "524 Ch Rastine", "Antibes");
		assertThrows(ConstraintViolationException.class, () -> patientService.create(patient13));
		
		Patient patient14 = new Patient("John", "Do", LocalDate.of(2222, 8, 30), "Male", "555-3456", "524 Ch Rastine", "Antibes");
		assertThrows(ConstraintViolationException.class, () -> patientService.create(patient14));
	}

}
