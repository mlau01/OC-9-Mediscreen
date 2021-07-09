package com.mediscreen.mediscreendiabetesia.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mediscreen.mediscreendiabetesia.proxy.Patient;
import com.mediscreen.mediscreendiabetesia.proxy.PatientProxy;

@Service
public class PatientServiceImpl implements IPatientService{
	
	PatientProxy patientProxy;
	
	@Autowired
	public PatientServiceImpl(PatientProxy p_patientProxy) {
		patientProxy = p_patientProxy;
	}
	
	/**
	 * Get a patient using Feign proxy
	 * @param patientId
	 * @return Patient
	 * 9 juil. 2021
	 */
	public Patient getPatient(int patientId) {
		return patientProxy.getPatient(patientId);
	}

	/**
	 * Get a patient using Feign proxy
	 * @param lastName
	 * @return Patient
	 * 9 juil. 2021
	 */
	public Patient getPatient(String lastName) {
		return patientProxy.getPatient(lastName);
	}

}
