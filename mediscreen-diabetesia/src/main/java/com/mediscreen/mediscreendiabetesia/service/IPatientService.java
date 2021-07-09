package com.mediscreen.mediscreendiabetesia.service;

import com.mediscreen.mediscreendiabetesia.proxy.Patient;

public interface IPatientService {
	
	public Patient getPatient(int patientId);
	public Patient getPatient(String lastName);

}
