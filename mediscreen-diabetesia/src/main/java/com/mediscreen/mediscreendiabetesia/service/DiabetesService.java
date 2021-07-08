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

import com.mediscreen.mediscreendiabetesia.dto.PatientAssessDto;
import com.mediscreen.mediscreendiabetesia.proxy.Note;
import com.mediscreen.mediscreendiabetesia.proxy.Patient;
import com.mediscreen.mediscreendiabetesia.utils.AgeRange;
import com.mediscreen.mediscreendiabetesia.utils.RiskLevel;
import com.mediscreen.mediscreendiabetesia.utils.RiskRule;

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
	
	/**
	 * Build a PatientAssessDto containing desired information of a patient
	 * @param pid Patient ID
	 * @return PatientAssessDto filled
	 * 8 juil. 2021
	 */
	public PatientAssessDto getPatientAssess(int pid) {
		PatientAssessDto patientAssessDto = new PatientAssessDto();
		Patient patient = patientService.getPatient(pid);
		
		patientAssessDto.setFirstName(patient.getFirstName());
		patientAssessDto.setLastName(patient.getLastName());
		patientAssessDto.setAge(ageOf(patient.getDateOfBirth(), LocalDate.now()));
		patientAssessDto.setRiskLevel(getDiabetesRiskLevel(patient));
		
		return patientAssessDto;
		
	}
	
	/**
	 * Calculate RiskLevel of a patient by comparing patient/note informations to rules
	 * @param Patient patient to examine
	 * @return RiskLevel representing the diabetes risk
	 * 8 juil. 2021
	 */
	public RiskLevel getDiabetesRiskLevel(Patient patient) {
		RiskLevel result = RiskLevel.None;
		
		
		List<Note> patientNotes = noteService.getAllPatientNotes(patient.getId());
		int pTriggerCount = triggerService.getTriggerCount(patientNotes);
		int pAge = ageOf(patient.getDateOfBirth(), LocalDate.now());
		String pSex = patient.getSex();
		
		List<RiskRule> rules = getRules();
		for(RiskRule riskParam : rules){
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
						logger.debug("-------------------------------------------------------------------");
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
	public List<RiskRule> getRules() {
		List<RiskRule> rules = new ArrayList<RiskRule>();
		rules.add(new RiskRule(2, new AgeRange(30, 200), null, RiskLevel.Borderline));
		rules.add(new RiskRule(3, new AgeRange(0, 30), "M", RiskLevel.InDanger));
		rules.add(new RiskRule(4, new AgeRange(0, 30), "F", RiskLevel.InDanger));
		rules.add(new RiskRule(4, new AgeRange(30, 200), null, RiskLevel.InDanger));
		rules.add(new RiskRule(5, new AgeRange(0, 30), "M", RiskLevel.EarlyOnset));
		rules.add(new RiskRule(7, new AgeRange(0, 30), "F", RiskLevel.EarlyOnset));
		rules.add(new RiskRule(8, new AgeRange(30, 200), null, RiskLevel.EarlyOnset));
		
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
