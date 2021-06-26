package com.abernathyclinic.mediscreen.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	public PatientController(IPatientService p_patientService){
		patientService = p_patientService;
	}
	
	@Deprecated
	@PostMapping(value = "patient/add") //For old curl request
	public ResponseEntity<String> oldAddPatient(@Valid Patient patient) {
    	log.info("POST Request to /patient/add with value: {}", patient);
    	
    	return addPatient(patient);
	}

	//CRUD
	//POST
	@PostMapping(value = "patient")
	public ResponseEntity<String> addPatient(@Valid @RequestBody Patient patient) {
		
		
    	log.info("POST Request to /patient with value: {}", patient);
    	
		try {
			Patient createdPatient = patientService.create(patient);
			return new ResponseEntity<String>("\"Patient id: " + createdPatient.getId() + " created\"", HttpStatus.CREATED);
		} catch (AlreadyExistsPatientException e) {
			log.warn("POST Request to /patient return error: {}", e.getMessage());
			return new ResponseEntity<String>("\"" + e.getMessage() + "\"", HttpStatus.BAD_REQUEST);
		}
    	
	}
	
	//GET
	@ApiOperation(value = "Get the patient list")
	@GetMapping(value = "patient")
	public ResponseEntity<List<Patient>> listPatient() {
		log.info("Get Request to /patient");
		List<Patient> listPatient = patientService.getAllPatient();
		if(listPatient == null) {
			log.error("Internal error object List<Patient> is null");
			return new ResponseEntity<List<Patient>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<List<Patient>>(listPatient, HttpStatus.OK);
	}
	
	//UPDATE
	@PutMapping(value = "patient")
	public ResponseEntity<String> updatePatient(@Valid @RequestBody Patient patient)  {

    	log.info("PUT Request to /patient/update with value: {}", patient);
    	
		Patient updatedPatient = null;
		try {
			updatedPatient = patientService.update(patient);
		} catch (NoSuchPatientException e) {
			log.error(e.getMessage());
			return new ResponseEntity<String>("\"Patient id: " + patient.getId() + " not found\"", HttpStatus.NOT_FOUND);
		} catch (AlreadyExistsPatientException e) {
			log.warn("POST Request to /patient return error: {}", e.getMessage());
			return new ResponseEntity<String>("\"" + e.getMessage() + "\"", HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<String>("\"Patient id: " + updatedPatient.getId() + " updated\"", HttpStatus.CREATED);
	}
	
	//DELETE
	@DeleteMapping(value = "patient/{id}")
	public ResponseEntity<String> deletePatient(@PathVariable("id") String id)  {

    	log.info("DELETE Request to /patient/delete/{}", id);

		try {
			patientService.delete(id);
		} catch (NoSuchPatientException e) {
			log.error(e.getMessage());
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>( HttpStatus.OK);
	}

}
