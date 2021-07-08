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
import com.mediscreen.mediscreendiabetesia.service.DiabetesService;
import com.mediscreen.mediscreendiabetesia.utils.RiskLevel;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
public class DiabetesControllerTest {
	

		@Autowired
		private MockMvc mockMvc;

		@MockBean
		private DiabetesService diabetesService;
		
		
		@Test
		public void controllerEndpointAssessByIdTest_shouldCallServiceAndCorrectResponse() throws Exception {
			PatientAssessDto patientAssessDto = new PatientAssessDto();
			
			patientAssessDto.setFirstName("John");
			patientAssessDto.setLastName("Do");
			patientAssessDto.setAge(19);
			patientAssessDto.setRiskLevel(RiskLevel.Borderline);
			
			when(diabetesService.getPatientAssess(99)).thenReturn(patientAssessDto);
			
			 mockMvc.perform(get("/assess/99"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.firstName", is("John")))
            .andExpect(jsonPath("$.lastName", is("Do")))
            .andExpect(jsonPath("$.age", is(19)))
            .andExpect(jsonPath("$.riskLevel", is(RiskLevel.Borderline.toString())));
			
			verify(diabetesService, Mockito.times(1)).getPatientAssess(99);
		}
		
}
