package com.mediscreen.mediscreendiabetesia.service;

import java.util.List;

import com.mediscreen.mediscreendiabetesia.proxy.Note;

public interface INoteService {
	
	public List<Note> getAllPatientNotes(int patientId);

}
