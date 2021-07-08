package com.mediscreen.mediscreendiabetesia;

import com.mediscreen.mediscreendiabetesia.proxy.Note;
import com.mediscreen.mediscreendiabetesia.proxy.Patient;
import com.mediscreen.mediscreendiabetesia.utils.RiskLevel;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class PatientTest {
	String lastName;
	Patient patient;
	Note[] notes;
	RiskLevel riskLevel;
}
