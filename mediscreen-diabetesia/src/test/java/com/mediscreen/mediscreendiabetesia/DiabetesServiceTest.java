package com.mediscreen.mediscreendiabetesia;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mediscreen.mediscreendiabetesia.service.DiabetesService;
import com.mediscreen.mediscreendiabetesia.service.NoteService;
import com.mediscreen.mediscreendiabetesia.service.PatientService;
import com.mediscreen.mediscreendiabetesia.service.TriggerService;

@ExtendWith(MockitoExtension.class)
public class DiabetesServiceTest {
	
	@Mock
	NoteService noteService;
	
	@Mock
	PatientService patientService;
	
	@Mock
	TriggerService triggerService;
	
	@Test
	public void AgeOfTest_shouldReturnCorrectAge() {
		DiabetesService diabetesService = new DiabetesService(noteService, patientService, triggerService);
		
		LocalDate birth = LocalDate.of(1986, 8, 30);
		
		LocalDate date1 = LocalDate.of(2020, 8, 30);
		LocalDate date2 = LocalDate.of(2020, 8, 29);
		LocalDate date3 = LocalDate.of(2021, 8, 30);
		
		
		assertEquals(34, diabetesService.ageOf(birth, date1));
		assertEquals(33, diabetesService.ageOf(birth, date2));
		assertEquals(33, diabetesService.ageOf(birth, date2));
		assertEquals(35, diabetesService.ageOf(birth, date3));
		
		
		
	}

}
