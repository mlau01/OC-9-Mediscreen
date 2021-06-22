package com.abernathyclinic.mediscreen.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.abernathyclinic.mediscreen.model.Patient;

@Repository
public interface PatientRepository extends CrudRepository<Patient, Integer> {

	boolean existsByFirstNameAndLastName(String firstName, String lastName);
	Patient findByFirstNameAndLastName(String firstName, String lastName);
}
