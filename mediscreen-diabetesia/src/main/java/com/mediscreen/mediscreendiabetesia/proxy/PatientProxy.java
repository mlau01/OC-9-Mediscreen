package com.mediscreen.mediscreendiabetesia.proxy;

import feign.Param;
import feign.RequestLine;

public interface PatientProxy {

	
	@RequestLine("GET /patients/{patientId}")
	Patient getPatient(@Param("patientId") int patientId);

	@RequestLine("POST /patients/{patient}")
	Patient addPatient(@Param("patient") String string);

}
