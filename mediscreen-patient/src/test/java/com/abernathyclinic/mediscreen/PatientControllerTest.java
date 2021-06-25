package com.abernathyclinic.mediscreen;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.abernathyclinic.mediscreen.model.Patient;
import com.abernathyclinic.mediscreen.repository.PatientRepository;
import com.abernathyclinic.mediscreen.service.PatientService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
public class PatientControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PatientService patientService;

	@Autowired
	private ObjectMapper oMapper;

	@Test
	public void postPatientTest_shouldPerformPostWithoutErrors() throws Exception {
		LocalDate dateOfBirth = LocalDate.of(1984, 8, 30);
		String firstName = "John";
		String lastName = "Do";
		String phone = "555-5534";
		String sex = "M";
		String address = "524 Ch Rastine";
		String city = "Antibes";

		Patient patient = new Patient(firstName, lastName, dateOfBirth, sex, phone, address, city);
		when(patientService.create(any(Patient.class))).thenReturn(patient);
		
		mockMvc.perform(post("/patient")
				.contentType(MediaType.APPLICATION_JSON)
				.content(oMapper.writeValueAsString(patient)))
				.andExpect(status().isCreated());
		
		verify(patientService, Mockito.times(1)).create(any(Patient.class));

	}

	@Test
	public void deletePatientTest_() throws Exception {
	
		mockMvc.perform(delete("/patient/25")).andExpect(status().isOk());
		verify(patientService, Mockito.times(1)).delete(anyString());
	}
	
	@Test
	public void getAllPatientTest() throws Exception {
		when(patientService.getAllPatient()).thenReturn(new ArrayList<Patient>());
		
		mockMvc.perform(get("/patient")).andExpect(status().isOk());
		
		verify(patientService, Mockito.times(1)).getAllPatient();
	}
	
	@Test
	public void putPatientTest() throws Exception {
		LocalDate dateOfBirth = LocalDate.of(1984, 8, 30);
		String firstName = "John";
		String lastName = "Do";
		String phone = "555-5534";
		String sex = "M";
		String address = "524 Ch Rastine";
		String city = "Antibes";

		Patient patient = new Patient(firstName, lastName, dateOfBirth, sex, phone, address, city);
		when(patientService.update(any(Patient.class))).thenReturn(patient);
		
		mockMvc.perform(put("/patient")
				.contentType(MediaType.APPLICATION_JSON)
				.content(oMapper.writeValueAsString(patient)))
				.andExpect(status().isCreated());
		
		verify(patientService, Mockito.times(1)).update(any(Patient.class));
		
	}

}
