package com.abernathyclinic.mediscreen.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.abernathyclinic.mediscreen.model.Patient;

@Repository
public interface PatientRepository extends MongoRepository<Patient, String> {

}
