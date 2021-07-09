package com.mediscreen.mediscreendiabetesia.service;

import java.util.List;

import com.mediscreen.mediscreendiabetesia.proxy.Note;

public interface INoteService {
	
	/**
	 * Should return all notes of a patient
	 * @param patientId
	 * 9 juil. 2021
	 */
	public List<Note> getAllPatientNotes(int patientId);

}
