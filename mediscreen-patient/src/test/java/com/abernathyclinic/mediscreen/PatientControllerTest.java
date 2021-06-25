package com.abernathyclinic.mediscreen;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

@SpringBootTest
@AutoConfigureMockMvc
public class PatientControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private PatientRepository patientRepo;
	
	@Test
	public void postPatientTest_shouldPerformPostAndDeleteWithoutErrors() throws Exception {
		
		String json = "{\"firstName\":\"Mathias\",\"lastName\":\"LAUER\",\"dateOfBirth\":\"1986-08-30\",\"sex\":\"M\",\"address\":\"5 Chemin du Parc de Vaugrenier\",\"city\":\"ANTIBES\",\"phone\":\"0660328349\"}";
		String urlencoded = "firstName=Mathias&lastName=LAUER&dob=1986-08-30&sex=M&address=5+Chemin+du+Parc+de+Vaugrenier&city=ANTIBES&phone=0660328349";
        
		mockMvc.perform(
	            post("/patient/add?" + urlencoded))
	            .andExpect(status().isCreated());
		
		Optional<Patient> patient = patientRepo.findByFirstNameAndLastName("Mathias", "Lauer");
		
		
		mockMvc.perform(
	            delete("/patient/delete/" + patient.get().getId()))
	            .andExpect(status().isOk());
	}
	
}
