package com.abernathyclinic.mediscreen;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.abernathyclinic.mediscreen.model.Patient;
import com.abernathyclinic.mediscreen.repository.PatientRepository;
import com.abernathyclinic.mediscreen.service.PatientService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class PatientControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private PatientRepository patientRepo;
	
	@Autowired
	private ObjectMapper oMapper;
	
	@Test
	public void postPatientTest_shouldPerformPostAndDeleteWithoutErrors() throws Exception {
		
		String json = "{\"firstName\":\"Mathias\",\"lastName\":\"LAUER\",\"dateOfBirth\":\"1986-08-30\",\"sex\":\"M\",\"address\":\"5 Chemin du Parc de Vaugrenier\",\"city\":\"ANTIBES\",\"phone\":\"0660328349\"}";
		String urlencoded = "firstName=Mathias&lastName=LAUER&dob=1986-08-30&sex=M&address=5+Chemin+du+Parc+de+Vaugrenier&city=ANTIBES&phone=0660328349";
		
		LocalDate dateOfBirth = LocalDate.of(1984, 8, 30);
		String firstName = "John";
		String lastName = "Do";
		String phone = "555-5534";
		String sex = "M";
		String address = "524 Ch Rastine";
		String city = "Antibes";
		
		Patient patient = new Patient(firstName, lastName, dateOfBirth, sex, phone, address, city);
		
		mockMvc.perform(
	            post("/patient/add")
				.content(oMapper.writeValueAsString(patient)));
	            
		/*
		Optional<Patient> patient = patientRepo.findByFirstNameAndLastName("Mathias", "Lauer");
		
		
		mockMvc.perform(
	            delete("/patient/delete/" + patient.get().getId()))
	            .andExpect(status().isOk());
	            */
	}
	
}
