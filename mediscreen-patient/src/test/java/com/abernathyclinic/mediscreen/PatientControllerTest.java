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
				.content(oMapper.writeValueAsString(patient))).andExpect(status().isCreated());
	            
		
		Optional<Patient> db_patient = patientRepo.findByFirstNameAndLastName("John", "Do");
		
		
		mockMvc.perform(
	            delete("/patient/delete/" + db_patient.get().getId()))
	            .andExpect(status().isOk());
	            
	}
	
}
