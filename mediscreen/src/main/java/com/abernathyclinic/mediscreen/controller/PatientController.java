package com.abernathyclinic.mediscreen.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;

import com.abernathyclinic.mediscreen.model.Patient;
import com.abernathyclinic.mediscreen.service.IPatientService;

@Controller
public class PatientController {
	
	private static Logger log = LoggerFactory.getLogger(PatientController.class);
	
	private IPatientService patientService;
	
	@Autowired
	public PatientController(IPatientService p_patientService)
	{
		patientService = p_patientService;
	}
	
	@PostMapping(value = "patient/add")
	public String addPatient(@Valid Patient patient, BindingResult result, Model model) {
		
		
    	log.info("POST Request to /patient/add with value: {}", patient);
    	
    	if(result.hasErrors()) {
    		log.warn("POST Request to /patient/add return errors, data: {}", patient);
    		return "patient/addform";
    	}
    	else {
    		patientService.create(patient);
    	}
        
    	return "redirect:/patient/list";
	}

}
