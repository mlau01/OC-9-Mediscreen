package com.mediscreen.mediscreendiabetesia.service;

import java.util.List;

import com.mediscreen.mediscreendiabetesia.proxy.Note;

public interface ITriggerService {
	
	public List<String> getTriggers();
	public int getTriggerCount(List<Note> patientNotes);

}
