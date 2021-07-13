package com.mediscreen.mediscreendiabetesia.model;

import com.mediscreen.mediscreendiabetesia.proxy.Note;
import com.mediscreen.mediscreendiabetesia.proxy.Patient;
import com.mediscreen.mediscreendiabetesia.utils.RiskLevel;

public class PatientTestModel {
	private String lastName;
	private Patient patient;
	private Note[] notes;
	private RiskLevel riskLevel;
	
	
	
	public PatientTestModel(String lastName, Patient patient, Note[] notes, RiskLevel riskLevel) {
		super();
		this.lastName = lastName;
		this.patient = patient;
		this.notes = notes;
		this.riskLevel = riskLevel;
	}
	
	
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Patient getPatient() {
		return patient;
	}
	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	public Note[] getNotes() {
		return notes;
	}
	public void setNotes(Note[] notes) {
		this.notes = notes;
	}
	public RiskLevel getRiskLevel() {
		return riskLevel;
	}
	public void setRiskLevel(RiskLevel riskLevel) {
		this.riskLevel = riskLevel;
	}
	
	
}
