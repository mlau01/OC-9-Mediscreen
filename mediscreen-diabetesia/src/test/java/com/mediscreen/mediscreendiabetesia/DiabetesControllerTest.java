package com.mediscreen.mediscreendiabetesia;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.mediscreen.mediscreendiabetesia.service.DiabetesService;
@SpringBootTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
public class DiabetesControllerTest {
	

		@Autowired
		private MockMvc mockMvc;

		@MockBean
		private DiabetesService diabetesService;
		
		/*
		@Test
		public void controllerEndpointAssessByIdTest_shouldCallServiceAndCorrectResponse() {
			when(diabetesService.getDiabetesRiskLevel(anyInt())).thenReturn(new ArrayList<NoteModel>());
			
			mockMvc.perform(get(CRUD_ENDPOINT_NAME + "/patient/0")).andExpect(status().isOk());
			
			verify(noteService, Mockito.times(1)).getByPatientIdOrderedDesc(0);
		}
		*/
}
