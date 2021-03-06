package com.mediscreen.mediscreendiabetesia.service;

import com.mediscreen.mediscreendiabetesia.proxy.Patient;

public interface IPatientService {
	
	/**
	 * Should return a Patient object model filled with patient information
	 * 9 juil. 2021
	 */
	public Patient getPatient(int patientId);
	
	/**
	 * Should return a Patient object model filled with patient information
	 * 9 juil. 2021
	 */
	public Patient getPatient(String lastName);

}
