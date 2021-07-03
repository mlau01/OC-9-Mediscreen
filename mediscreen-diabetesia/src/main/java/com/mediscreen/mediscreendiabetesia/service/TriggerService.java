package com.mediscreen.mediscreendiabetesia.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mediscreen.mediscreendiabetesia.proxy.Note;

@Service
public class TriggerService {
	
	/**
	 * List of trigger term
	 * @return List<String>
	 * 3 juil. 2021
	 */
	public List<String> getTriggers(){
		List<String> triggers = new ArrayList<String>();
		
		triggers.add("Hémoglobine A1C");
		triggers.add("Microalbumine");
		triggers.add("Taille");
		triggers.add("Poids");
		triggers.add("Fumeur");
		triggers.add("Anormal");
		triggers.add("Cholestérol");
		triggers.add("Vertige");
		triggers.add("Rechute");
		triggers.add("Réaction");
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
		int count = 0;
		for(Note note : patientNotes) {
			String content = note.getNote().toLowerCase();
			for(String trigger : getTriggers()) {
				if(content.contains(trigger.toLowerCase())) {
					count++;
				}
			}
		}
		
		return count;
	}

}
