package com.mediscreen.mediscreendiabetesia.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.mediscreen.mediscreendiabetesia.dto.PatientAssessDto;
import com.mediscreen.mediscreendiabetesia.exception.NoSuchPatientException;
import com.mediscreen.mediscreendiabetesia.service.IDiabetesService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Controller
public class DiabetesController {
	private static Logger logger = LoggerFactory.getLogger(DiabetesController.class);
	private IDiabetesService diabetesService;
	
	@Autowired
	public DiabetesController(IDiabetesService p_diabetesService) {
		this.diabetesService = p_diabetesService;
	}
	
	//READ
	@ApiOperation(value = "Get a patient assessment")
	@ApiResponses(value = { @ApiResponse(code = 404, message = "No patient found with this id") })
	@GetMapping(value =  "assess/{id}")
	public ResponseEntity<PatientAssessDto> getPatientAssessDto(@PathVariable("id") int id) {
		logger.info("GET request to {}", "assess/" + id);
	
		try {
			return new ResponseEntity<PatientAssessDto>(diabetesService.getPatientAssess(id), HttpStatus.OK);
		} catch (NoSuchPatientException e) {
			logger.error(e.getMessage());
			return new ResponseEntity<PatientAssessDto>(HttpStatus.NOT_FOUND);
		}

	}
	
}
