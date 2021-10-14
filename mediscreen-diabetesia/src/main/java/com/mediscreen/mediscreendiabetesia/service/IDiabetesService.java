package com.mediscreen.mediscreendiabetesia.service;

import com.mediscreen.mediscreendiabetesia.dto.PatientAssessDto;
import com.mediscreen.mediscreendiabetesia.exception.NoSuchPatientException;

public interface IDiabetesService {
	
	/**
	 * Should return a PatientAssessDto filled by diabetes assessment result
	 * @param patientId
	 * 9 juil. 2021
	 */
	public PatientAssessDto getPatientAssessById(String patientId) throws NoSuchPatientException;
	
	/**
	 * Should return a PatientAssessDto filled by diabetes assessment result
	 * @param lastName
	 * 9 juil. 2021
	 */
	public PatientAssessDto getPatientAssessByLastName(String lastName) throws NoSuchPatientException;

}
