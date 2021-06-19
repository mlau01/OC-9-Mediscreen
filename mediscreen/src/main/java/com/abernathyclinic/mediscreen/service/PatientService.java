package com.abernathyclinic.mediscreen.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abernathyclinic.mediscreen.model.Patient;
import com.abernathyclinic.mediscreen.repository.PatientRepository;

@Service
public class PatientService implements IPatientService {
	
	@Autowired
	private PatientRepository patientRepository;
	
	public Patient create(Patient patient) {
		return patientRepository.save(patient);
	}

}
