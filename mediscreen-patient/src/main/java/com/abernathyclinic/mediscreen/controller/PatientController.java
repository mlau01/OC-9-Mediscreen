package com.abernathyclinic.mediscreen.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abernathyclinic.mediscreen.exception.AlreadyExistsPatientException;
import com.abernathyclinic.mediscreen.exception.NoSuchPatientException;
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
	
	//CRUD
	
	//POST
	@PostMapping(value = "patient/add")
	public ResponseEntity<Patient> addPatient(@Valid Patient patient) {
		
		
    	log.info("POST Request to /patient/add with value: {}", patient);
    	
		try {
			return new ResponseEntity<Patient>(patientService.create(patient), HttpStatus.CREATED);
		} catch (AlreadyExistsPatientException e) {
			log.warn("POST Request to /patient/add return error: {}", e.getMessage());
			return new ResponseEntity<Patient>(HttpStatus.BAD_REQUEST);
		}
    	
	}
	
	//GET
	@ApiOperation(value = "Get the patient list")
	@GetMapping(value = "patient/list")
	public ResponseEntity<Iterable<Patient>> listPatient() {
		log.info("Get Request to /patient/list");
		Iterable<Patient> iterablePatient = patientService.getAllPatient();
		if(iterablePatient == null) {
			log.error("Internal error object Iterable<Patient> is null");
			return new ResponseEntity<Iterable<Patient>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Iterable<Patient>>(iterablePatient, HttpStatus.OK);
	}
	
	//UPDATE
	@PutMapping(value = "patient/update")
	public ResponseEntity<Patient> updatePatient(@Valid Patient patient)  {

    	log.info("PUT Request to /patient/update with value: {}", patient);
    	
		Patient updatedPatient = null;
		try {
			updatedPatient = patientService.update(patient);
		} catch (NoSuchPatientException e) {
			log.error(e.getMessage());
			return new ResponseEntity<Patient>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Patient>(updatedPatient , HttpStatus.CREATED);
	}
	
	//DELETE
	@DeleteMapping(value = "patient/delete")
	public ResponseEntity<String> deletePatient(@Valid Patient patient)  {

    	log.info("DELETE Request to /patient/delete with value: {}", patient);

		try {
			patientService.delete(patient);
		} catch (NoSuchPatientException e) {
			log.error(e.getMessage());
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>( HttpStatus.OK);
	}

}
