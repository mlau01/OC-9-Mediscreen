package com.abernathyclinic.mediscreen.service;

import java.util.List;
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
	/**
	 * Create a patient
	 * @param patient to create
	 * @return Patient created if succeed
	 * @throws AlreadyExistPatientException when a patient with this full name already exists
	 * 28 juin 2021
	 */
	public Patient create(Patient patient) throws AlreadyExistsPatientException {
		if(patientRepository.existsByFirstNameAndLastName(patient.getFirstName(), patient.getLastName())) {
			throw new AlreadyExistsPatientException("Patient " + patient.getFirstName() + " " + patient.getLastName() + " already exists");
		}
		return patientRepository.save(patient);
	}

	@Override
	/**
	 * Get a patient by his id
	 * @param id
	 * @return Patient if found
	 * @throw NoSuchPatientException when not found
	 * 28 juin 2021
	 */
	public Patient read(Integer id) throws NoSuchPatientException {
		Optional<Patient> patient = patientRepository.findById(id);
		if( ! patient.isPresent()) {
			throw new NoSuchPatientException("Patient with id: " + id + " not found");
		}
		return patient.get();
	}

	@Override
	/**
	 * Update a patient, if the patient id do not exists, create a new patient
	 * @param patient to update, will be searched by the id present in data
	 * @return Patient updated if succeed
	 * @throw AlreadyExistsPatientException when the full name chosen already exist
	 * 28 juin 2021
	 */
	public Patient update(Patient patient) throws AlreadyExistsPatientException {
		Optional<Patient> odb_patient = patientRepository.findById(patient.getId());
		if( ! odb_patient.isPresent()) {
			create(patient);
		}
		Patient db_patient = odb_patient.get();
		
		if( (! db_patient.getFirstName().equals(patient.getFirstName())) || (! db_patient.getLastName().equals(patient.getLastName()))) {
			if(patientRepository.existsByFirstNameAndLastName(patient.getFirstName(), patient.getLastName())) {
				throw new AlreadyExistsPatientException("Patient " + patient.getFirstName() + " " + patient.getLastName() + " already exists");
			}
		}
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
	/**
	 * Delete a patient
	 * @param patient to delete, searched by the id present in data
	 * @throw NoSuchPatientException when no patient with this id was found
	 * 28 juin 2021
	 */
	public void delete(String id) throws NoSuchPatientException {
		Optional<Patient> odb_patient = patientRepository.findById(Integer.parseInt(id));
		if( ! odb_patient.isPresent()) {
			throw new NoSuchPatientException("Patient with id " + id + " not found");
		}

		patientRepository.delete(odb_patient.get());

		
	}

	@Override
	/**
	 * Get all patients list
	 * @return List<Patient>
	 * 28 juin 2021
	 */
	public List<Patient> getAllPatient() {
		List<Patient> patients = patientRepository.findAll();
		return patients;
	}

	/**
	 * Get a patient by his last name
	 * if multiple patient have the same name, return the first occurrence
	 * @param lastname
	 * @return Patient
	 * 8 juil. 2021
	 */
	@Override
	public Patient getByLastName(String lastname) throws NoSuchPatientException {
		Optional<Patient> patient = patientRepository.findByLastName(lastname);
		if( ! patient.isPresent()) {
			throw new NoSuchPatientException("Patient with last name: " + lastname + " not found");
		}
		return patient.get();
	}

}
