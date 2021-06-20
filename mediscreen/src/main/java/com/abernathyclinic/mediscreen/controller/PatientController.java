package com.abernathyclinic.mediscreen.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.abernathyclinic.mediscreen.exception.PatientAlreadyExistsException;
import com.abernathyclinic.mediscreen.model.Patient;
import com.abernathyclinic.mediscreen.service.IPatientService;

@Controller
public class PatientController {
	
	private static Logger log = LoggerFactory.getLogger(PatientController.class);
	
	private IPatientService patientService;
	
	private final String TEMPLATE_ADD_PATIENT = "addpatient";
	private final String TEMPLATE_LIST_PATIENT = "listpatient";
	
	@Autowired
	public PatientController(IPatientService p_patientService)
	{
		patientService = p_patientService;
	}
	
	//TODO Manage http status error code
	@PostMapping(value = "patient/add")
	public String addPatient(@Valid Patient patient, BindingResult result, Model model) {
		
		
    	log.info("POST Request to /patient/add with value: {}", patient);
    	
    	if(result.hasErrors()) {
    		log.warn("POST Request to /patient/add return errors, data: {}", patient);
    		return TEMPLATE_ADD_PATIENT;
    	}
    	else {
    		try {
				patientService.create(patient);
			} catch (PatientAlreadyExistsException e) {
				log.warn("POST Request to /patient/add return error: {}", e.getMessage());
				result.addError(new FieldError("lastName", "lastName", e.getMessage()));
				return TEMPLATE_ADD_PATIENT;
			}
    	}
        
    	return "redirect:" + TEMPLATE_LIST_PATIENT;
	}
	
	@GetMapping(value = "patient/list")
	public String listPatient(Model model) {
		model.addAttribute("patients", patientService.getAllPatient());
		
		return TEMPLATE_LIST_PATIENT;
	}

}
