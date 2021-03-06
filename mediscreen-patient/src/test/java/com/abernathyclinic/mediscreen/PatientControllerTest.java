package com.abernathyclinic.mediscreen;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.abernathyclinic.mediscreen.exception.AlreadyExistsPatientException;
import com.abernathyclinic.mediscreen.exception.NoSuchPatientException;
import com.abernathyclinic.mediscreen.model.Patient;
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
	
	private static Patient patientTest;
	
	@BeforeAll
	public static void setUp() {
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
	public void postPatientTest_shouldReturnStatusCreated() throws Exception {
	
		when(patientService.create(any(Patient.class))).thenReturn(patientTest);
		
		mockMvc.perform(post("/patients")
				.contentType(MediaType.APPLICATION_JSON)
				.content(oMapper.writeValueAsString(patientTest)))
				.andExpect(status().isCreated());
		
		verify(patientService, Mockito.times(1)).create(any(Patient.class));

	}
	
	@Test
	public void postAlreadyExistsPatientTest_shouldReturnError400() throws Exception {
	
		when(patientService.create(any(Patient.class))).thenThrow(AlreadyExistsPatientException.class);
		
		mockMvc.perform(post("/patients")
				.contentType(MediaType.APPLICATION_JSON)
				.content(oMapper.writeValueAsString(patientTest)))
				.andExpect(status().isBadRequest());
		
		verify(patientService, Mockito.times(1)).create(any(Patient.class));

	}

	@Test
	public void deletePatientTest_shouldReturnStatusOk() throws Exception {
		
		mockMvc.perform(delete("/patients/0"))
				.andExpect(status().isOk());
		
		verify(patientService, Mockito.times(1)).delete("0");
	}
	
	@Test
	public void getAllPatientTest_shouldReturnStatusOk() throws Exception {
		when(patientService.getAllPatient()).thenReturn(new ArrayList<Patient>());
		
		mockMvc.perform(get("/patients")).andExpect(status().isOk());
		
		verify(patientService, Mockito.times(1)).getAllPatient();
	}
	
	@Test
	public void getNullPatientTest_shouldReturnError500() throws Exception {
		when(patientService.getAllPatient()).thenReturn(null);
		
		mockMvc.perform(get("/patients")).andExpect(status().isInternalServerError());
		
		verify(patientService, Mockito.times(1)).getAllPatient();
	}
	
	@Test
	public void getSinglePatientTest_shouldReturnStatusOk() throws Exception {
		when(patientService.read(0)).thenReturn(patientTest);
		
		mockMvc.perform(get("/patients/0")).andExpect(status().isOk());
		
		verify(patientService, Mockito.times(1)).read(0);
	}
	
	@Test
	public void getSinglePatientUnparseableIdTest_shouldReturnStatusBadRequest() throws Exception {
		when(patientService.read(0)).thenThrow(NumberFormatException.class);
		
		mockMvc.perform(get("/patients/0")).andExpect(status().isBadRequest());
		
		verify(patientService, Mockito.times(1)).read(0);
	}
	
	@Test
	public void getSingleUnknownPatientTest_shouldReturnStatusNotFound() throws Exception {
		when(patientService.read(0)).thenThrow(NoSuchPatientException.class);
		
		mockMvc.perform(get("/patients/0")).andExpect(status().isNotFound());
		
		verify(patientService, Mockito.times(1)).read(0);
	}
	
	@Test
	public void getPatientByLastNameTest_shouldReturnStatusOk() throws Exception {
		when(patientService.getByLastName("TEST")).thenReturn(patientTest);
		
		mockMvc.perform(get("/patients/lastname/TEST")).andExpect(status().isOk());
		
		verify(patientService, Mockito.times(1)).getByLastName("TEST");
	}
	
	@Test
	public void getUnknownPatientByLastNameTest_shouldReturnStatus404() throws Exception {
		when(patientService.getByLastName("TEST")).thenThrow(NoSuchPatientException.class);
		
		mockMvc.perform(get("/patients/lastname/TEST")).andExpect(status().isNotFound());
		
		verify(patientService, Mockito.times(1)).getByLastName("TEST");
	}
	
	@Test
	public void putPatientTest_shouldReturnStatusCreated() throws Exception {
		when(patientService.update(any(Patient.class))).thenReturn(patientTest);
		
		mockMvc.perform(put("/patients")
				.contentType(MediaType.APPLICATION_JSON)
				.content(oMapper.writeValueAsString(patientTest)))
				.andExpect(status().isCreated());
		
		verify(patientService, Mockito.times(1)).update(any(Patient.class));
		
	}
	
	@Test
	public void putAlreadyExistPatientTest_shouldReturnStatusBadRequest() throws Exception {
		when(patientService.update(any(Patient.class))).thenThrow(AlreadyExistsPatientException.class);
		
		mockMvc.perform(put("/patients")
				.contentType(MediaType.APPLICATION_JSON)
				.content(oMapper.writeValueAsString(patientTest)))
				.andExpect(status().isBadRequest());
		
		verify(patientService, Mockito.times(1)).update(any(Patient.class));
		
	}

}
