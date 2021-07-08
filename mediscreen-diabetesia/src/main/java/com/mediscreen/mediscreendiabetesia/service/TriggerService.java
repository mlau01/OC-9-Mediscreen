package com.mediscreen.mediscreendiabetesia.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mediscreen.mediscreendiabetesia.proxy.Note;

@Service
public class TriggerService {
	
	private static Logger logger = LoggerFactory.getLogger(TriggerService.class);
	
	/**
	 * List of trigger term
	 * @return List<String>
	 * 3 juil. 2021
	 */
	public List<String> getTriggers(){
		List<String> triggers = new ArrayList<String>();
		
		triggers.add("H�moglobine A1C");
		triggers.add("Microalbumine");
		triggers.add("Taille");
		triggers.add("Poids");
		triggers.add("Fumeur");
		triggers.add("Anormal");
		triggers.add("Cholest�rol");
		triggers.add("Vertige");
		triggers.add("Rechute");
		triggers.add("R�action");
		triggers.add("Anticorps");
		
		return triggers;
	}
	
	/**
	 * Get the number of trigger term found in a list of note
	 * Not case sensitive
	 * @param List<Note>
	 * @return number of term found
	 * 3 juil. 2021
	 */
	public int getTriggerCount(List<Note> patientNotes) {
		logger.trace("Looking for trigger in patient notes...");
		int count = 0;
		for(Note note : patientNotes) {
			String content = note.getNote().toLowerCase();
			for(String trigger : getTriggers()) {
				if(content.contains(trigger.toLowerCase())) {
					logger.trace("Found trigger: " + trigger);
					count++;
				}
			}
		}
		
		logger.trace("Found a total of " + count + " triggers");
		
		return count;
	}

}