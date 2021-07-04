package com.mediscreen.mediscreendiabetesia;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doReturn;
import static org.mockito.ArgumentMatchers.any;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mediscreen.mediscreendiabetesia.proxy.Patient;
import com.mediscreen.mediscreendiabetesia.proxy.Note;
import com.mediscreen.mediscreendiabetesia.service.DiabetesService;
import com.mediscreen.mediscreendiabetesia.service.NoteService;
import com.mediscreen.mediscreendiabetesia.service.PatientService;
import com.mediscreen.mediscreendiabetesia.service.TriggerService;
import com.mediscreen.mediscreendiabetesia.utils.AgeRange;
import com.mediscreen.mediscreendiabetesia.utils.RiskLevel;
import com.mediscreen.mediscreendiabetesia.utils.RiskParam;

@ExtendWith(MockitoExtension.class)
public class DiabetesServiceTest {
	
	@Mock
	NoteService noteService;
	
	@Mock
	PatientService patientService;
	
	@Mock
	TriggerService triggerService;
	
	@Test
	public void AgeOfTest_shouldReturnCorrectAge() {
		DiabetesService diabetesService = new DiabetesService(noteService, patientService, triggerService);
		
		LocalDate birth = LocalDate.of(1986, 8, 30);
		
		LocalDate date1 = LocalDate.of(2020, 8, 30);
		LocalDate date2 = LocalDate.of(2020, 8, 29);
		LocalDate date3 = LocalDate.of(2021, 8, 30);
		
		
		assertEquals(34, diabetesService.ageOf(birth, date1));
		assertEquals(33, diabetesService.ageOf(birth, date2));
		assertEquals(33, diabetesService.ageOf(birth, date2));
		assertEquals(35, diabetesService.ageOf(birth, date3));
	}
	
	@Test
	public void getRulesTest_shouldReturnOrderingRulesByTriggerLimitDesc() {
		DiabetesService diabetesService = new DiabetesService(noteService, patientService, triggerService);
		List<RiskParam> rules = diabetesService.getRules();
		
		Iterator<RiskParam> it = rules.iterator();
		RiskParam previous = null;
		while(it.hasNext()) {
			if(previous == null) {
				previous = it.next();
			}
			else {
				RiskParam current = it.next();
				assertTrue(current.getTriggerLimit() <= previous.getTriggerLimit());
				previous = current;
			}
		}
	}
	
	@Test
	public void getDiabetesRiskLevelTest_shouldReturnCorrectRiskLevelForEachPatient() {
		DiabetesService diabetesService = new DiabetesService(noteService, patientService, triggerService);
		
		//Patient EarlyOnSet
		doReturn(9).when(triggerService).getTriggerCount(any(List.class));
		Patient earlyOnSetPatient1 = new Patient(LocalDate.of(1986, 8, 30), "M");
		doReturn(earlyOnSetPatient1).when(patientService).getPatient(0);
		
		assertEquals(RiskLevel.EarlyOnset, diabetesService.getDiabetesRiskLevel(0));
		
		verify(patientService, Mockito.times(1)).getPatient(0);
		
		//Patient EarlyOnSet 2
		doReturn(8).when(triggerService).getTriggerCount(any(List.class));
		Patient earlyOnSetPatient2 = new Patient(LocalDate.of(1995, 8, 30), "F");
		doReturn(earlyOnSetPatient2).when(patientService).getPatient(1);
		
		assertEquals(RiskLevel.EarlyOnset, diabetesService.getDiabetesRiskLevel(1));
		
		verify(patientService, Mockito.times(1)).getPatient(1);
		
	}
	
	/*
	 	rules.put(new RiskParam(2, new AgeRange(30, 200), null), RiskLevel.Borderline);
		rules.put(new RiskParam(3, new AgeRange(0, 30), "M"), RiskLevel.InDanger);
		rules.put(new RiskParam(4, new AgeRange(0, 30), "F"), RiskLevel.InDanger);
		rules.put(new RiskParam(4, new AgeRange(30, 200), null), RiskLevel.InDanger);
		rules.put(new RiskParam(5, new AgeRange(0, 30), "M"), RiskLevel.EarlyOnset);
		rules.put(new RiskParam(7, new AgeRange(0, 30), "F"), RiskLevel.EarlyOnset);
		rules.put(new RiskParam(8, new AgeRange(30, 200), null), RiskLevel.EarlyOnset);
		
	 */
}
