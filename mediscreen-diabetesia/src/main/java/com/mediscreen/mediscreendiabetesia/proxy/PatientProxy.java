package com.mediscreen.mediscreendiabetesia.proxy;

import java.util.List;

import feign.Headers;
import feign.Param;
import feign.RequestLine;

public interface PatientProxy {
	@RequestLine("GET /patients/{patientId}")
	Patient getPatient(@Param("patientId") int patientId);
	
	@RequestLine("POST /patients")
	@Headers("Content-Type: application/json;charset=UTF-8")
	Patient addPatient(String string);
	
	@RequestLine("GET /patients")
	List<Patient> getAllPatients();

}