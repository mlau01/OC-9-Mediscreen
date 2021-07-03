package com.mediscreen.mediscreendiabetesia;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.mediscreen.mediscreendiabetesia.proxy.NoteProxy;
import com.mediscreen.mediscreendiabetesia.proxy.PatientProxy;

public class DiabeteIAModuleTest {
	
	@Test
	public void getNoteProxy_shouldReturnNoteProxyModule() {
		DiabetesIAModule diabetesIaModule = new DiabetesIAModule();
		
		NoteProxy noteProxy = diabetesIaModule.getProxyNote("http://localhost:8082");
		
		assertNotNull(noteProxy);
	}
	
	@Test
	public void getPatientProxy_shouldReturnPatientProxyModule() {
		DiabetesIAModule diabetesIaModule = new DiabetesIAModule();
		
		PatientProxy patientProxy = diabetesIaModule.getProxyPatient("http://localhost:8081");
		
		assertNotNull(patientProxy);
		
	}
}
