package com.abernathyclinic.mediscreen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.abernathyclinic.mediscreen.exception.AlreadyExistsPatientException;
import com.abernathyclinic.mediscreen.exception.NoSuchPatientException;
import com.abernathyclinic.mediscreen.model.Patient;
import com.abernathyclinic.mediscreen.service.PatientService;

@SpringBootTest
public class PatientServiceTest {

	private static Patient patientTest;
	
	@Autowired
	private PatientService patientService;
	
	@Test
	public void listPatientsTest_shouldReturnPatientList() {
		patientService.getAllPatient().forEach(patient -> assertNotNull(patient.getId()));
		
	}
	
	@BeforeAll
	public static void setUpPatientTest() {
		//Set data
		LocalDate dateOfBirth = LocalDate.of(1984, 8, 30);
		String firstName = "John";
		String lastName = "Do";
		String phone = "555-5534";
		String sex = "M";
		String address = "524 Ch Rastine";
		String city = "Antibes";
		
		patientTest = new Patient(firstName, lastName, dateOfBirth, sex, phone, address, city);
	}
	
	@Test
	public void patientModelTest_gettersAndSettersShouldWorkCorrectly() {
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
		patient.setPhone(phone);
		patient.setAddress(address);
		patient.setSex(sex);
		patient.setCity(city);
		patient.setDateOfBirth(dateOfBirth);
		
		assertEquals(firstName, patient.getFirstName());
		assertEquals(lastName, patient.getLastName());
		assertEquals(phone, patient.getPhone());
		assertEquals(sex, patient.getSex());
		assertEquals(address, patient.getAddress());
		assertEquals(city, patient.getCity());
		assertEquals(dateOfBirth, patient.getDateOfBirth());
		
		String firstName2 = "Walter";
		String lastName2 = "Who";
		
		patient.setDob("1986-08-30");
		patient.setGiven(firstName2);
		patient.setFamily(lastName2);
		
		assertEquals(firstName2, patient.getFirstName());
		assertEquals(lastName2, patient.getLastName());
		assertEquals(LocalDate.of(1986, 8, 30), patient.getDateOfBirth());
	}
	
	@Test
	public void createNewPatientThenDeleteTest_shouldCreatePatientCorrectly() throws AlreadyExistsPatientException, NoSuchPatientException {
		Patient createdPatient = patientService.create(patientTest);
		
		//Assert
		assertNotNull(patientService.read(createdPatient.getId()));
		assertEquals(patientTest.getFirstName(), createdPatient.getFirstName());
		assertEquals(patientTest.getLastName(), createdPatient.getLastName());
		assertEquals(patientTest.getDateOfBirth(), createdPatient.getDateOfBirth());
		assertEquals(patientTest.getPhone(), createdPatient.getPhone());
		assertEquals(patientTest.getSex(), createdPatient.getSex());
		assertEquals(patientTest.getAddress(), createdPatient.getAddress());
		assertEquals(patientTest.getCity(), createdPatient.getCity());
		
		//Clean up
		patientService.delete(String.valueOf(createdPatient.getId()));
		assertThrows(NoSuchPatientException.class, () -> patientService.read(createdPatient.getId()));
	}
	
	@Test
	public void createDuplicatePatientTest_shouldThrowException() throws AlreadyExistsPatientException, NoSuchPatientException {
		Patient patientCreated = patientService.create(patientTest);
		assertThrows(AlreadyExistsPatientException.class, () -> patientService.create(patientTest));
		
		//Clean up
		patientService.delete(String.valueOf(patientCreated.getId()));
		assertThrows(NoSuchPatientException.class, () -> patientService.read(patientCreated.getId()));
	}
	
	@Test
	public void getPatientByLastNameTest_shouldReturnPatientByLastName() throws AlreadyExistsPatientException, NoSuchPatientException {
		Patient createdPatient = patientService.create(patientTest);
		
		assertNotNull(patientService.getByLastName(createdPatient.getLastName()));
		
		//Clean up
		patientService.delete(String.valueOf(createdPatient.getId()));
		assertThrows(NoSuchPatientException.class, () -> patientService.read(createdPatient.getId()));
	}
	
	@Test
	public void getPatientByUnknownLastNameTest_shouldThrowException() {
		assertThrows(NoSuchPatientException.class, () -> patientService.getByLastName("AZEERZRZR"));
	}
	
	@Test
	public void updatePatientTest_shouldUpdatePatientCorrectly() throws AlreadyExistsPatientException, NoSuchPatientException {
		
		Patient patientCreated = patientService.create(patientTest);
		
		//Update the patient
		LocalDate m_dateOfBirth = LocalDate.of(1914, 1, 2);
		String m_firstName = "Berth";
		String m_lastName = "Alister";
		String m_phone = "1234-12345";
		String m_sex = "F";
		String m_address = "19 Rue Cartel";
		String m_city = "Nice";
		
		Patient patient = new Patient();
		patient.setId(patientCreated.getId());
		patient.setFirstName(m_firstName);
		patient.setLastName(m_lastName);
		patient.setAddress(m_address);
		patient.setDateOfBirth(m_dateOfBirth);
		patient.setSex(m_sex);
		patient.setPhone(m_phone);
		patient.setCity(m_city);
		
		Patient updatedPatient = patientService.update(patient);
		
		//Assert
		assertEquals(m_firstName, updatedPatient.getFirstName());
		assertEquals(m_lastName, updatedPatient.getLastName());
		assertEquals(m_dateOfBirth, updatedPatient.getDateOfBirth());
		assertEquals(m_phone, updatedPatient.getPhone());
		assertEquals(m_sex, updatedPatient.getSex());
		assertEquals(m_address, updatedPatient.getAddress());
		assertEquals(m_city, updatedPatient.getCity());
		
		//Clean up
		patientService.delete(String.valueOf(patientCreated.getId()));
		assertThrows(NoSuchPatientException.class, () -> patientService.read(patientCreated.getId()));
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
