package com.abernathyclinic.mediscreen.exception;

public class PatientAlreadyExistsException extends Exception{
	
	public PatientAlreadyExistsException() {
		super();
	}
	
	public PatientAlreadyExistsException(String s) {
		super(s);
	}
}
