package com.mediscreen.mediscreendiabetesia.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mediscreen.mediscreendiabetesia.proxy.Patient;
import com.mediscreen.mediscreendiabetesia.proxy.PatientProxy;

@Service
public class PatientService {
	
	PatientProxy patientProxy;
	
	@Autowired
	public PatientService(PatientProxy p_patientProxy) {
		patientProxy = p_patientProxy;
	}
	
	public Patient getPatient(int patientId) {
		return patientProxy.getPatient(patientId);
	}

	public Patient getPatient(String lastName) {
		return patientProxy.getPatient(lastName);
	}

}
