package com.mediscreen.mediscreendiabetesia.service;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.TemporalUnit;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mediscreen.mediscreendiabetesia.proxy.Note;
import com.mediscreen.mediscreendiabetesia.proxy.Patient;
import com.mediscreen.mediscreendiabetesia.utils.AgeRange;
import com.mediscreen.mediscreendiabetesia.utils.RiskLevel;
import com.mediscreen.mediscreendiabetesia.utils.RiskParam;

@Service
public class DiabetesService {
	
	private NoteService noteService;
	private PatientService patientService;
	private TriggerService triggerService;

	
	@Autowired
	public DiabetesService(NoteService noteService, PatientService patientService, TriggerService triggerService) {
		this.noteService = noteService;
		this.patientService = patientService;
		this.triggerService = triggerService;
	}
	
	public RiskLevel getDiabetesAssess(int pid) {
		RiskLevel result = RiskLevel.None;
		
		Patient patient = patientService.getPatient(pid);
		List<Note> patientNotes = noteService.getAllPatientNotes(pid);
		int triggerCount = triggerService.getTriggerCount(patientNotes);
		int age = ageOf(patient.getDateOfBirth(), LocalDate.now());
		String sex = patient.getSex();
		RiskParam patientParam = new RiskParam(triggerCount, new AgeRange(age,age), sex);
		
		getRules().forEach((riskLevel, riskParam) -> {
			//TODO Find a compare method
		});
		
		
		return result;
	}
	
	public Map<RiskLevel, RiskParam> getRules() {
		Map<RiskLevel, RiskParam> rules = new Hashtable<RiskLevel, RiskParam>();
		rules.put(RiskLevel.Borderline, new RiskParam(2, new AgeRange(30, 200), null));
		rules.put(RiskLevel.InDanger, new RiskParam(3, new AgeRange(0, 30), "M"));
		rules.put(RiskLevel.InDanger, new RiskParam(4, new AgeRange(0, 30), "F"));
		rules.put(RiskLevel.InDanger, new RiskParam(4, new AgeRange(30, 200), null));
		rules.put(RiskLevel.EarlyOnset, new RiskParam(5, new AgeRange(0, 30), "M"));
		rules.put(RiskLevel.EarlyOnset, new RiskParam(7, new AgeRange(0, 30), "F"));
		rules.put(RiskLevel.EarlyOnset, new RiskParam(8, new AgeRange(30, 200), null));
		
		return rules;
		
	}
	
	/**
	 * Calculate age based on birthday and today
	 * @param date LocalDate of birthday
	 * @param now LocalDate of now
	 * @return age in years
	 * 3 juil. 2021
	 */
	public int ageOf(LocalDate date, LocalDate now) {
		Period age = Period.between(date, now);
		
		return age.getYears();
	}

}
