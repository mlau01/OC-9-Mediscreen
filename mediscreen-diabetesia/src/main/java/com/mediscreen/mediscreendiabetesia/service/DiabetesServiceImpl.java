package com.mediscreen.mediscreendiabetesia.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mediscreen.mediscreendiabetesia.dto.PatientAssessDto;
import com.mediscreen.mediscreendiabetesia.exception.NoSuchPatientException;
import com.mediscreen.mediscreendiabetesia.proxy.Note;
import com.mediscreen.mediscreendiabetesia.proxy.Patient;
import com.mediscreen.mediscreendiabetesia.utils.RiskLevel;
import com.mediscreen.mediscreendiabetesia.utils.RiskRule;

import feign.FeignException.NotFound;

@Service
public class DiabetesServiceImpl implements IDiabetesService {
	
	private static Logger logger = LoggerFactory.getLogger(DiabetesServiceImpl.class);
	
	private INoteService noteService;
	private IPatientService patientService;
	private ITriggerService triggerService;
	private IRuleService ruleService;

	
	@Autowired
	public DiabetesServiceImpl(INoteService noteService, IPatientService patientService, ITriggerService triggerService, IRuleService ruleService) {
		this.noteService = noteService;
		this.patientService = patientService;
		this.triggerService = triggerService;
		this.ruleService = ruleService;
	}
	

	@Override
	public PatientAssessDto getPatientAssess(String lastName) throws NoSuchPatientException {
		Patient patient = null;
		try {
			patient = patientService.getPatient(lastName);
		} catch (NotFound e) {
			logger.error(""+e);
			throw new NoSuchPatientException("Patient with last name: " + lastName + " not found");
		}
		
		return buildPatientAssessDto(patient);
	}
	
	/**
	 * Build a PatientAssessDto containing desired information of a patient
	 * @param pid Patient ID
	 * @return PatientAssessDto filled
	 * 8 juil. 2021
	 * @throws NoSuchPatientException 
	 */
	public PatientAssessDto getPatientAssess(int pid) throws NoSuchPatientException {
		
		Patient patient = null;
		try {
			patient = patientService.getPatient(pid);
		} catch (NotFound e) {
			logger.error(""+e);
			throw new NoSuchPatientException("Patient with id: " + pid + " not found");
		}
		

		return buildPatientAssessDto(patient);
	}
	
	/**
	 * Private method to build the PatientAssessDto
	 * @param patient
	 * @return PatientAssessDto
	 * 8 juil. 2021
	 */
	private PatientAssessDto buildPatientAssessDto(Patient patient) {
		PatientAssessDto patientAssessDto = new PatientAssessDto();
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
		logger.debug("Start assessment of patient id: " + patient.getId() 
		+ ", name: " + patient.getFirstName() + " " + patient.getLastName());
		RiskLevel result = RiskLevel.None;
		
		List<Note> patientNotes = noteService.getAllPatientNotes(patient.getId());
		int pTriggerCount = triggerService.getTriggerCount(patientNotes);
		int pAge = ageOf(patient.getDateOfBirth(), LocalDate.now());
		String pSex = patient.getSex();
		
		List<RiskRule> rules = ruleService.getRules();
		for(RiskRule riskRule : rules){
			int triggerLimit = riskRule.getTriggerLimit();
			int ageMin = riskRule.getAgeRange().getStart();
			int ageMax = riskRule.getAgeRange().getEnd();
			String sex = riskRule.getSex();
			
			logger.debug("Start examine Rules(" + riskRule 
					+ ") for patient with triggerCount: " + pTriggerCount 
					+ ", age: " + pAge + ", sex: " + pSex);
			if(pTriggerCount >= triggerLimit) {
				logger.debug("pTriggerCount: " + pTriggerCount + " >= triggerLimit: " + triggerLimit);
				if(pAge >= ageMin  && pAge < ageMax) {
					logger.debug("Patient age: " + pAge + " >= ageMin:" + ageMin + " && < " + ageMax);
					if(sex == null || pSex.equals(sex)) {
						logger.debug("Patient sex: " + pSex + " == " + sex + " OR rule sex is null");
						logger.debug("Returning result: " + riskRule.getRiskLevel());
						return riskRule.getRiskLevel();
					}
				}
			}
		}
		return result;
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
