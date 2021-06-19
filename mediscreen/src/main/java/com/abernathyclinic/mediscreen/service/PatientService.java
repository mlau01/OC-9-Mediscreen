package com.abernathyclinic.mediscreen.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abernathyclinic.mediscreen.exception.PatientAlreadyExistsException;
import com.abernathyclinic.mediscreen.model.Patient;
import com.abernathyclinic.mediscreen.repository.PatientRepository;

@Service
public class PatientService implements IPatientService {
	
	@Autowired
	private PatientRepository patientRepository;
	
	@Override
	public Patient create(Patient patient) throws PatientAlreadyExistsException {
		if(patientRepository.existsByFirstNameAndLastName(patient.getFirstName(), patient.getLastName())) {
			throw new PatientAlreadyExistsException("Patient already exists");
		}
		return patientRepository.save(patient);
	}

	@Override
	public Patient read(Integer id) {
		return patientRepository.findById(id).get();
	}

	@Override
	public Patient update(Patient patient) {
		return patientRepository.save(patient);
	}

	@Override
	public void delete(Patient patient) {
		patientRepository.delete(patient);
	}

}
