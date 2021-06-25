package com.abernathyclinic.mediscreen.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abernathyclinic.mediscreen.exception.NoSuchPatientException;
import com.abernathyclinic.mediscreen.exception.AlreadyExistsPatientException;
import com.abernathyclinic.mediscreen.model.Patient;
import com.abernathyclinic.mediscreen.repository.PatientRepository;

@Service
public class PatientService implements IPatientService {
	
	@Autowired
	private PatientRepository patientRepository;
	
	@Override
	public Patient create(Patient patient) throws AlreadyExistsPatientException {
		if(patientRepository.existsByFirstNameAndLastName(patient.getFirstName(), patient.getLastName())) {
			throw new AlreadyExistsPatientException("Patient " + patient.getFirstName() + " " + patient.getLastName() + " already exists");
		}
		return patientRepository.save(patient);
	}

	@Override
	public Patient read(Integer id) throws NoSuchPatientException {
		Optional<Patient> patient = patientRepository.findById(id);
		if( ! patient.isPresent()) {
			throw new NoSuchPatientException("Patient with id: " + id + " not found");
		}
		return patient.get();
	}

	@Override
	public Patient update(Patient patient) throws NoSuchPatientException {
		Optional<Patient> odb_patient = patientRepository.findById(patient.getId());
		if( ! odb_patient.isPresent()) {
			throw new NoSuchPatientException("Patient with id: " + patient.getId() + " not found");
		}
		Patient db_patient = odb_patient.get();
		
		db_patient.setFirstName(patient.getFirstName());
		db_patient.setLastName(patient.getLastName());
		db_patient.setAddress(patient.getAddress());
		db_patient.setDateOfBirth(patient.getDateOfBirth());
		db_patient.setSex(patient.getSex());
		db_patient.setPhone(patient.getPhone());
		db_patient.setCity(patient.getCity());
		
		return patientRepository.save(db_patient);
		
	}

	@Override
	public void delete(Patient patient) throws NoSuchPatientException {
		Optional<Patient> odb_patient = patientRepository.findByFirstNameAndLastName(patient.getFirstName(), patient.getLastName());
		if( ! odb_patient.isPresent()) {
			throw new NoSuchPatientException("Patient with fullname: " + patient.getFirstName() + " " + patient.getLastName() + " not found");
		}

		patientRepository.delete(odb_patient.get());

		
	}

	@Override
	public Iterable<Patient> getAllPatient() {
		Iterable<Patient> patients = patientRepository.findAll();
		return patients;
	}

}
