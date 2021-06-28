package com.abernathyclinic.mediscreen.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
	@ApiOperation(value = "Add a patient")
	@PostMapping(value = "patient")
	public ResponseEntity<String> addPatient(@Valid @RequestBody Patient patient) {
		
		
    	log.info("POST Request to /patient with body: {}", patient);
    	
		try {
			patientService.create(patient);
			return new ResponseEntity<String>(HttpStatus.CREATED);
		} catch (AlreadyExistsPatientException e) {
			log.warn("POST Request to /patient return error: {}", e.getMessage());
			return new ResponseEntity<String>(jsonify(e.getMessage()), HttpStatus.BAD_REQUEST);
		}
    	
	}
	
	//GET ALL
	@ApiOperation(value = "Get all patients list")
	@GetMapping(value = "patients")
	public ResponseEntity<List<Patient>> listPatient() {
		log.info("Get Request to /patients");
		List<Patient> listPatient = patientService.getAllPatient();
		if(listPatient == null) {
			log.error("Internal error object List<Patient> is null");
			return new ResponseEntity<List<Patient>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<List<Patient>>(listPatient, HttpStatus.OK);
	}

	//READ
	@ApiOperation(value = "Get a patient by id")
	@GetMapping(value = "patient/{id}")
	public ResponseEntity<Patient> getPatient(@PathVariable("id") String id) {
		log.info("GET request to /patient/{}", id);
		try {
			return new ResponseEntity<Patient>(patientService.read(Integer.valueOf(id)), HttpStatus.OK);
		} catch (NumberFormatException e) {
			log.error(e.getMessage());
			return new ResponseEntity<Patient>(HttpStatus.BAD_REQUEST);
		} catch (NoSuchPatientException e) {
			log.error(e.getMessage());
			return new ResponseEntity<Patient>(HttpStatus.NOT_FOUND);
		}
	}
	
	//UPDATE
	@ApiOperation(value = "Update a patient")
	@PutMapping(value = "patient")
	public ResponseEntity<String> updatePatient(@Valid @RequestBody Patient patient)  {

    	log.info("PUT Request to /patient with value: {}", patient);
 
		try {
			patientService.update(patient);
		} catch (NoSuchPatientException e) {
			log.error(e.getMessage());
			return new ResponseEntity<String>(jsonify("Patient id: " + patient.getId() + " not found"), HttpStatus.NOT_FOUND);
		} catch (AlreadyExistsPatientException e) {
			log.error("POST Request to /patient return error: {}", e.getMessage());
			return new ResponseEntity<String>(jsonify(e.getMessage()), HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<String>(HttpStatus.CREATED);
	}
	
	//DELETE
	@ApiOperation(value = "Delete a patient")
	@DeleteMapping(value = "patient")
	public ResponseEntity<String> deletePatient(@RequestBody Patient patient)  {

    	log.info("DELETE Request to /patient with body: {}", patient);

		try {
			patientService.delete(patient);
		} catch (NoSuchPatientException e) {
			log.error(e.getMessage());
			return new ResponseEntity<String>(jsonify(e.getMessage()), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>( HttpStatus.OK);
	}

	private String jsonify(String string){
		return "\"" + string + "\"";
	}

}
