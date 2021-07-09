package com.mediscreen.mediscreendiabetesia.service;

import java.util.List;

import com.mediscreen.mediscreendiabetesia.proxy.Note;

public interface ITriggerService {
	
	/**
	 * Should provide a list of diabetes terms that trigger risk
	 * 9 juil. 2021
	 */
	public List<String> getTriggers();
	
	/**
	 * Should parse patient notes to count number of trigger term found
	 * @return trigger term found
	 * 9 juil. 2021
	 */
	public int getTriggerCount(List<Note> patientNotes);

}
