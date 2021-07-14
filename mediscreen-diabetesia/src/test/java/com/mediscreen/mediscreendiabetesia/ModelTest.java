package com.mediscreen.mediscreendiabetesia;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import com.mediscreen.mediscreendiabetesia.dto.PatientAssessDto;
import com.mediscreen.mediscreendiabetesia.proxy.Note;
import com.mediscreen.mediscreendiabetesia.proxy.Patient;
import com.mediscreen.mediscreendiabetesia.utils.RiskLevel;

public class ModelTest {
	
	@Test
	public void noteModelTest_shouldSetAndGetCorrectly() {
		Patient patient = new Patient();
		patient.setId(1);
		patient.setFirstName("John");
		patient.setLastName("Do");
		
		LocalDate dob = LocalDate.of(1986, 8, 30);
		patient.setDateOfBirth(dob);
		patient.setAddress("54 Hello Street");
		patient.setPhone("123456789");
		patient.setCity("NC");
		patient.setSex("M");
		
		assertEquals(1, patient.getId());
		assertEquals("John", patient.getFirstName());
		assertEquals("Do", patient.getLastName());
		assertEquals(dob, patient.getDateOfBirth());
		assertEquals("54 Hello Street", patient.getAddress());
		assertEquals("123456789", patient.getPhone());
		assertEquals("NC", patient.getCity());
		assertEquals("M", patient.getSex());
	}
	
	@Test
	public void patientModelTest_shouldSetAndGetCorrectly() {
		Note note = new Note();
		
		note.setId("1");
		note.setAuthor("Dr Strange");
		LocalDateTime dateCreated = LocalDateTime.of(2020, 4, 30, 12, 00);
		note.setCreated(dateCreated);
		note.setNote("Hello World!");
		note.setPatientId(99);
		
		assertEquals("1", note.getId());
		assertEquals("Dr Strange", note.getAuthor());
		assertEquals(dateCreated, note.getCreated());
		assertEquals("Hello World!", note.getNote());
		assertEquals(99, note.getPatientId());
	}
	
	@Test
	public void patientAssessDtoTest_shouldSetAndGetCorrectly() {
		PatientAssessDto patientAssessDto = new PatientAssessDto();
		patientAssessDto.setFirstName("John");
		patientAssessDto.setLastName("Who");
		patientAssessDto.setAge(10);
		patientAssessDto.setRiskLevel(RiskLevel.None);
		
		assertEquals("John", patientAssessDto.getFirstName());
		assertEquals("Who", patientAssessDto.getLastName());
		assertEquals(10, patientAssessDto.getAge());
		assertEquals(RiskLevel.None, patientAssessDto.getRiskLevel());
	}

}
