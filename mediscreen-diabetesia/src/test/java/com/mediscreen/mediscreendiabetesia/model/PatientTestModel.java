package com.mediscreen.mediscreendiabetesia.model;

import com.mediscreen.mediscreendiabetesia.proxy.Note;
import com.mediscreen.mediscreendiabetesia.proxy.Patient;
import com.mediscreen.mediscreendiabetesia.utils.RiskLevel;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class PatientTestModel {
	String lastName;
	Patient patient;
	Note[] notes;
	RiskLevel riskLevel;
}
