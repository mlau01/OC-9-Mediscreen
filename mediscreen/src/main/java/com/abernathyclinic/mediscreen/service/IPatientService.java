package com.abernathyclinic.mediscreen.service;

import java.util.List;

import com.abernathyclinic.mediscreen.exception.PatientAlreadyExistsException;
import com.abernathyclinic.mediscreen.model.Patient;

public interface IPatientService {
	
	public Patient create(Patient patient) throws PatientAlreadyExistsException;
	public Patient read(Integer id);
	public Patient update(Patient patient);
	public void delete(Patient patient);
	public Iterable<Patient> getAllPatient();

}
