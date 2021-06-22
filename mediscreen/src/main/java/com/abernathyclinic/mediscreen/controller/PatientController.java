package com.abernathyclinic.mediscreen.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abernathyclinic.mediscreen.exception.PatientAlreadyExistsException;
import com.abernathyclinic.mediscreen.model.Patient;
import com.abernathyclinic.mediscreen.service.IPatientService;

import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins = "http://localhost:4200") //Allow HTTP request from angular
public class PatientController {
	
	private static Logger log = LoggerFactory.getLogger(PatientController.class);
	
	private IPatientService patientService;

	@Autowired
	public PatientController(IPatientService p_patientService)
	{
		patientService = p_patientService;
	}
	
	@PostMapping(value = "patient/add")
	public ResponseEntity<Patient> addPatient(@Valid Patient patient) {
		
		
    	log.info("POST Request to /patient/add with value: {}", patient);
    	
		try {
			return new ResponseEntity<Patient>(patientService.create(patient), HttpStatus.CREATED);
		} catch (PatientAlreadyExistsException e) {
			log.warn("POST Request to /patient/add return error: {}", e.getMessage());
			return new ResponseEntity<Patient>(HttpStatus.BAD_REQUEST);
		}
    	
	}
	
	@ApiOperation(value = "Get the patient list")
	@GetMapping(value = "patient/list")
	public ResponseEntity<Iterable<Patient>> listPatient() {
		log.info("Get Request to /patient/list");
		return new ResponseEntity<Iterable<Patient>>(patientService.getAllPatient(), HttpStatus.OK);
	}

}
