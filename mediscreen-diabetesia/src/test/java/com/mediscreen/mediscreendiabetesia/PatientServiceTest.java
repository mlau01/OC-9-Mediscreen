package com.mediscreen.mediscreendiabetesia;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mediscreen.mediscreendiabetesia.proxy.PatientProxy;
import com.mediscreen.mediscreendiabetesia.service.PatientServiceImpl;

@ExtendWith(MockitoExtension.class)
public class PatientServiceTest {
	
	@Mock
	PatientProxy patientProxy;

	@Test
	public void getPatientNoteTest_shouldCallNoteProxy() {
		PatientServiceImpl patientService = new PatientServiceImpl(patientProxy);
		
		patientService.getPatient(99);
		
		verify(patientProxy, Mockito.times(1)).getPatient(99);
	}
}
