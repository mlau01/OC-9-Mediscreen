package com.mediscreen.mediscreendiabetesia.service;

import com.mediscreen.mediscreendiabetesia.dto.PatientAssessDto;
import com.mediscreen.mediscreendiabetesia.exception.NoSuchPatientException;

public interface IDiabetesService {
	
	public PatientAssessDto getPatientAssess(int pid) throws NoSuchPatientException;
	public PatientAssessDto getPatientAssess(String lastName) throws NoSuchPatientException;

}
