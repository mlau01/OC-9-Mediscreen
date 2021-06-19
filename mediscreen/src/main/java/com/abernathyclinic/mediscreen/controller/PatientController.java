package com.abernathyclinic.mediscreen.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import com.abernathyclinic.mediscreen.model.Patient;
import com.abernathyclinic.mediscreen.service.IPatientService;

@Controller
public class PatientController {
	
	private IPatientService patientService;
	
	@Autowired
	public PatientController(IPatientService p_patientService)
	{
		patientService = p_patientService;
	}
	
	@PostMapping(value = "patient/add")
	public void addPatient(@Valid Patient patient) {
		
	}

}
