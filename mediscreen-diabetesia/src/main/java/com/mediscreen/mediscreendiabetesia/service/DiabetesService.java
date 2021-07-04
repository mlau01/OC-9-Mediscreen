package com.mediscreen.mediscreendiabetesia.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mediscreen.mediscreendiabetesia.proxy.Note;
import com.mediscreen.mediscreendiabetesia.proxy.Patient;
import com.mediscreen.mediscreendiabetesia.utils.AgeRange;
import com.mediscreen.mediscreendiabetesia.utils.RiskLevel;
import com.mediscreen.mediscreendiabetesia.utils.RiskParam;

@Service
public class DiabetesService {
	
	private static Logger logger = LoggerFactory.getLogger(DiabetesService.class);
	
	private NoteService noteService;
	private PatientService patientService;
	private TriggerService triggerService;

	
	@Autowired
	public DiabetesService(NoteService noteService, PatientService patientService, TriggerService triggerService) {
		this.noteService = noteService;
		this.patientService = patientService;
		this.triggerService = triggerService;
	}
	
	public RiskLevel getDiabetesRiskLevel(int pid) {
		RiskLevel result = RiskLevel.None;
		
		Patient patient = patientService.getPatient(pid);
		List<Note> patientNotes = noteService.getAllPatientNotes(pid);
		int pTriggerCount = triggerService.getTriggerCount(patientNotes);
		int pAge = ageOf(patient.getDateOfBirth(), LocalDate.now());
		String pSex = patient.getSex();
		
		List<RiskParam> rules = getRules();
		for(RiskParam riskParam : rules){
			int triggerLimit = riskParam.getTriggerLimit();
			int ageMin = riskParam.getAgeRange().getStart();
			int ageMax = riskParam.getAgeRange().getEnd();
			String sex = riskParam.getSex();
			
			logger.debug("-------------------------------------------------------------------");
			logger.debug("Start examine Rules(" + riskParam + ") for patient with triggerCount: " + pTriggerCount + ", age: " + pAge + ", sex: " + pSex);
			if(pTriggerCount >= triggerLimit) {
				logger.debug("pTriggerCount: " + pTriggerCount + " >= triggerLimit: " + triggerLimit);
				if(pAge >= ageMin  && pAge < ageMax) {
					logger.debug("Patient age: " + pAge + " >= ageMin:" + ageMin + " && < " + ageMax);
					if(sex == null || pSex.equals(sex)) {
						logger.debug("Patient sex: " + pSex + " == " + sex + " OR rule sex is null, returning: " + riskParam.getRiskLevel());
						return riskParam.getRiskLevel();
					}
				}
			}
			logger.debug("-------------------------------------------------------------------");
		}
		
		
		return result;
	}
	
	/**
	 * Return a sorted map by RiskParam.triggerLimit descendant
	 * @return SortedMap<RiskParam, RiskLevel>
	 * 4 juil. 2021
	 */
	public List<RiskParam> getRules() {
		List<RiskParam> rules = new ArrayList<RiskParam>();
		rules.add(new RiskParam(2, new AgeRange(30, 200), null, RiskLevel.Borderline));
		rules.add(new RiskParam(3, new AgeRange(0, 30), "M", RiskLevel.InDanger));
		rules.add(new RiskParam(4, new AgeRange(0, 30), "F", RiskLevel.InDanger));
		rules.add(new RiskParam(4, new AgeRange(30, 200), null, RiskLevel.InDanger));
		rules.add(new RiskParam(5, new AgeRange(0, 30), "M", RiskLevel.EarlyOnset));
		rules.add(new RiskParam(7, new AgeRange(0, 30), "F", RiskLevel.EarlyOnset));
		rules.add(new RiskParam(8, new AgeRange(30, 200), null, RiskLevel.EarlyOnset));
		
		return rules.stream().sorted().collect(Collectors.toList());
		
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
