package com.abernathyclinic.mediscreen.service;

import com.abernathyclinic.mediscreen.exception.AlreadyExistsPatientException;
import com.abernathyclinic.mediscreen.exception.NoSuchPatientException;
import com.abernathyclinic.mediscreen.model.Patient;

public interface IPatientService {
	
	public Patient create(Patient patient) throws AlreadyExistsPatientException;
	public Patient read(Integer id) throws NoSuchPatientException;
	public Patient update(Patient patient) throws NoSuchPatientException;
	public void delete(Patient patient) throws NoSuchPatientException;
	public Iterable<Patient> getAllPatient();

}
