package com.abernathyclinic.mediscreen.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.abernathyclinic.mediscreen.model.Patient;

@Repository
public interface PatientRepository extends CrudRepository<Patient, Integer> {

	boolean existsByFirstNameAndLastName(String firstName, String lastName);
	Optional<Patient> findByFirstNameAndLastName(String firstName, String lastName);
}
