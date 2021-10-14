package com.mediscreen.mediscreendiabetesia;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.mediscreen.mediscreendiabetesia.dto.PatientAssessDto;
import com.mediscreen.mediscreendiabetesia.exception.NoSuchPatientException;
import com.mediscreen.mediscreendiabetesia.service.DiabetesServiceImpl;
import com.mediscreen.mediscreendiabetesia.utils.RiskLevel;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
public class DiabetesControllerTest {
	

		@Autowired
		private MockMvc mockMvc;

		@MockBean
		private DiabetesServiceImpl diabetesService;
		
		
		@Test
		public void controllerEndpointAssessByIdTest_shouldCallServiceAndCorrectResponse() throws Exception {
			PatientAssessDto patientAssessDto = new PatientAssessDto();
			
			patientAssessDto.setFirstName("John");
			patientAssessDto.setLastName("Do");
			patientAssessDto.setAge(19);
			patientAssessDto.setRiskLevel(RiskLevel.Borderline);
			
			when(diabetesService.getPatientAssessById("99")).thenReturn(patientAssessDto);
			
			 mockMvc.perform(get("/assess/id")
			.param("patId", "99"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.firstName", is("John")))
            .andExpect(jsonPath("$.lastName", is("Do")))
            .andExpect(jsonPath("$.age", is(19)))
            .andExpect(jsonPath("$.riskLevel", is(RiskLevel.Borderline.toString())));
			
			verify(diabetesService, Mockito.times(1)).getPatientAssessById("99");
		}
		
		@Test
		public void assessByUnknownIdTest_shouldReturnNotFound() throws Exception {
			when(diabetesService.getPatientAssessById("99")).thenThrow(NoSuchPatientException.class);
			
			 mockMvc.perform(get("/assess/id")
			.param("patId", "99"))
           .andExpect(status().isNotFound());
			
			verify(diabetesService, Mockito.times(1)).getPatientAssessById("99");
		}
		
		@Test
		public void assessByEmptyIdTest_shouldReturnNotFound() throws Exception {
			when(diabetesService.getPatientAssessById("")).thenThrow(NoSuchPatientException.class);
			
			 mockMvc.perform(get("/assess/id")
			.param("patId", ""))
           .andExpect(status().isNotFound());
			
			verify(diabetesService, Mockito.times(1)).getPatientAssessById("");
		}
		
		@Test
		public void controllerEndpointAssessByLastNameTest_shouldCallServiceAndCorrectResponse() throws Exception {
			PatientAssessDto patientAssessDto = new PatientAssessDto();
			
			patientAssessDto.setFirstName("John");
			patientAssessDto.setLastName("Do");
			patientAssessDto.setAge(19);
			patientAssessDto.setRiskLevel(RiskLevel.Borderline);
			
			when(diabetesService.getPatientAssessByLastName("Do")).thenReturn(patientAssessDto);
			
			 mockMvc.perform(get("/assess/familyName")
			.param("familyName", "Do"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.firstName", is("John")))
            .andExpect(jsonPath("$.lastName", is("Do")))
            .andExpect(jsonPath("$.age", is(19)))
            .andExpect(jsonPath("$.riskLevel", is(RiskLevel.Borderline.toString())));
			
			verify(diabetesService, Mockito.times(1)).getPatientAssessByLastName("Do");
		}
		
		@Test
		public void assessByUnknownLastNameTest_shouldReturnNotFound() throws Exception {
			when(diabetesService.getPatientAssessByLastName("Do")).thenThrow(NoSuchPatientException.class);
			
			 mockMvc.perform(get("/assess/familyName")
			.param("familyName", "Do"))
           .andExpect(status().isNotFound());
			
			verify(diabetesService, Mockito.times(1)).getPatientAssessByLastName("Do");
		}
		
}
