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
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

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
	@ApiOperation(value = "Deprecated URI for old curl request, return same responses as /patient")
	@PostMapping(value = "patient/add") //For old curl request
	public ResponseEntity<String> oldAddPatient(@Valid Patient patient) {
    	log.info("POST Request to /patients/add with value: {}", patient);
    	
    	return addPatient(patient);
	}

	//CRUD
	//POST
	
	@ApiOperation(value = "Add a patient")
	 @ApiResponses(value = { 
			 @ApiResponse(code = 400, message = "Patient with this fullname already exist")
			 })
	@PostMapping(value = "patients")
	public ResponseEntity<String> addPatient(@Valid @RequestBody Patient patient) {
		
		
    	log.info("POST Request to /patients with body: {}", patient);
    	
		try {
			patientService.create(patient);
			return new ResponseEntity<String>(HttpStatus.CREATED);
		} catch (AlreadyExistsPatientException e) {
			log.warn("POST Request to /patients return error: {}", e.getMessage());
			return new ResponseEntity<String>(jsonify(e.getMessage()), HttpStatus.BAD_REQUEST);
		}
    	
	}
	
	//GET ALL
	@ApiOperation(value = "Get all patients list")
	 @ApiResponses(value = { 
			 @ApiResponse(code = 500, message = "Cannot retrieve patients list")
			 })
	@GetMapping(value = "patients")
	public ResponseEntity<List<Patient>> listPatient() {
		log.info("Get Request to /patients");
		List<Patient> listPatient = patientService.getAllPatient();
		if(listPatient == null) {
			log.error("Internal error object List<Patient> returned by service is null");
			return new ResponseEntity<List<Patient>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<List<Patient>>(listPatient, HttpStatus.OK);
	}

	//READ
	@ApiOperation(value = "Get a patient by id")
	 @ApiResponses(value = { 
			 @ApiResponse(code = 400, message = "Id parse error"),
			 @ApiResponse(code = 404, message = "No patient found with this id")
			 })
	@GetMapping(value = "patients/{id}")
	public ResponseEntity<Patient> getPatient(@PathVariable("id") String id) {
		log.info("GET request to /patients/{}", id);
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
	@ApiOperation(value = "Update an existing patient based on id or create it if not exists")
	 @ApiResponses(value = { 
			 @ApiResponse(code = 400, message = "Patient with this fullname already exist")
			 })
	@PutMapping(value = "patients")
	public ResponseEntity<String> updatePatient(@Valid @RequestBody Patient patient)  {

    	log.info("PUT Request to /patient with value: {}", patient);
 
		try {
			patientService.update(patient);
		} catch (AlreadyExistsPatientException e) {
			log.error("PUT Request to /patients return error: {}", e.getMessage());
			return new ResponseEntity<String>(jsonify(e.getMessage()), HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<String>(HttpStatus.CREATED);
	}
	
	//DELETE
	@ApiOperation(value = "Delete a patient")
	 @ApiResponses(value = { 
			 @ApiResponse(code = 404, message = "No patient found with this id")
			 })
	@DeleteMapping(value = "patients/{id}")
	public ResponseEntity<String> deletePatient(@PathVariable("id") String id)  {

    	log.info("DELETE Request to /patients/{}", id);

		try {
			patientService.delete(id);
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
