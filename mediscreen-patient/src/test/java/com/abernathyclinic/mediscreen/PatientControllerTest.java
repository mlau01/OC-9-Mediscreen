package com.abernathyclinic.mediscreen;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class PatientControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void postPatientTest_shouldPerformPostAndDeleteWithoutErrors() throws Exception {
		
		String json = "{\"firstName\":\"Mathias\",\"lastName\":\"LAUER\",\"dateOfBirth\":\"1986-08-30\",\"sex\":\"M\",\"address\":\"5 Chemin du Parc de Vaugrenier\",\"city\":\"ANTIBES\",\"phone\":\"0660328349\"}";
		String urlencoded = "firstName=Mathias&lastName=LAUER&dob=1986-08-30&sex=M&address=5+Chemin+du+Parc+de+Vaugrenier&city=ANTIBES&phone=0660328349";
        
		mockMvc.perform(
	            post("/patient/add?" + urlencoded)
	            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
	            .content(json))
	            .andExpect(status().isCreated());
		

		mockMvc.perform(
	            delete("/patient/delete?" + urlencoded)
	                    .contentType(MediaType.APPLICATION_JSON)
	                    .content(urlencoded))
	            		.andExpect(status().isOk());
	}
	
}
