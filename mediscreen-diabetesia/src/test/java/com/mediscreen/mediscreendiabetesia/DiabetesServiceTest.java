package com.mediscreen.mediscreendiabetesia;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doReturn;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mediscreen.mediscreendiabetesia.proxy.Patient;
import com.mediscreen.mediscreendiabetesia.proxy.Note;
import com.mediscreen.mediscreendiabetesia.service.DiabetesServiceImpl;
import com.mediscreen.mediscreendiabetesia.service.NoteServiceImpl;
import com.mediscreen.mediscreendiabetesia.service.PatientServiceImpl;
import com.mediscreen.mediscreendiabetesia.service.RuleServiceImpl;
import com.mediscreen.mediscreendiabetesia.service.TriggerServiceImpl;
import com.mediscreen.mediscreendiabetesia.utils.AgeRange;
import com.mediscreen.mediscreendiabetesia.utils.RiskLevel;
import com.mediscreen.mediscreendiabetesia.utils.RiskRule;

@ExtendWith(MockitoExtension.class)
public class DiabetesServiceTest {
	
	@Mock
	NoteServiceImpl noteService;
	
	@Mock
	PatientServiceImpl patientService;
	
	@Mock
	TriggerServiceImpl triggerService;
	
	@Mock
	RuleServiceImpl ruleService;
	
	@Test
	public void AgeOfTest_shouldReturnCorrectAge() {
		DiabetesServiceImpl diabetesService = new DiabetesServiceImpl(noteService, patientService, triggerService, ruleService);
		
		LocalDate birth = LocalDate.of(1986, 8, 30);
		
		LocalDate date1 = LocalDate.of(2020, 8, 30);
		LocalDate date2 = LocalDate.of(2020, 8, 29);
		LocalDate date3 = LocalDate.of(2021, 8, 30);
		
		
		assertEquals(34, diabetesService.ageOf(birth, date1));
		assertEquals(33, diabetesService.ageOf(birth, date2));
		assertEquals(33, diabetesService.ageOf(birth, date2));
		assertEquals(35, diabetesService.ageOf(birth, date3));
	}
	
	private void diabetesRiskLevelTest(int iteration, int triggerCount, int birthYear, String sex, RiskLevel riskLevelExpected) {
		DiabetesServiceImpl diabetesService = new DiabetesServiceImpl(noteService, patientService, triggerService, ruleService);
		
		doReturn(triggerCount).when(triggerService).getTriggerCount(any(List.class));
		when(noteService.getAllPatientNotes(iteration)).thenReturn(new ArrayList<Note>());
		when(ruleService.getRules()).thenReturn(getRules());
		
		assertEquals(riskLevelExpected, diabetesService.getDiabetesRiskLevel(new Patient(iteration, LocalDate.of(birthYear, 8, 30), sex)));
		
		verify(noteService, Mockito.times(1)).getAllPatientNotes(iteration);
	}
	
	
	@Test
	public void multipleDiabetesRiskLevelTest_shouldAlwaysReturnConnectRiskLevel() {
		//Patient 1 EarlyOnSet - 9 trigger, Male older than 30
		diabetesRiskLevelTest(1, 9, 1986, "M", RiskLevel.EarlyOnset);
		
		//Patient 2 EarlyOnSet - 8 trigger, Female youngest than 30
		diabetesRiskLevelTest(2, 8, 1995, "F", RiskLevel.EarlyOnset);
		
		//Patient 3 Without any risk - 3 trigger, Female youngest than 30
		diabetesRiskLevelTest(3, 3, 1995, "F", RiskLevel.None);
		
		//Patient 4 Without any risk - 2 trigger, Male youngest than 30
		diabetesRiskLevelTest(4, 2, 1995, "M", RiskLevel.None);
		
		//Patient 5 Borderline - 2 trigger, Male older than 30
		diabetesRiskLevelTest(5, 2, 1986, "M", RiskLevel.Borderline);
		
		//Patient 6 Borderline - 2 trigger, Female older than 30
		diabetesRiskLevelTest(6, 2, 1986, "F", RiskLevel.Borderline);
		
		//Patient 7 InDanger - 4 trigger, Male youngest than 30
		diabetesRiskLevelTest(7, 4, 1995, "M", RiskLevel.InDanger);
		
		//Patient 8 InDanger - 6 trigger, Female youngest than 30
		diabetesRiskLevelTest(8, 6, 1995, "F", RiskLevel.InDanger);
		
		//Patient 9 EarlyOnSet - 6 trigger, Female youngest than 30
		diabetesRiskLevelTest(9, 6, 1995, "M", RiskLevel.EarlyOnset);
	}
	
	private List<RiskRule> getRules() {
		List<RiskRule> rules = new ArrayList<RiskRule>();
		rules.add(new RiskRule(2, new AgeRange(30, 200), null, RiskLevel.Borderline));
		rules.add(new RiskRule(3, new AgeRange(0, 30), "M", RiskLevel.InDanger));
		rules.add(new RiskRule(4, new AgeRange(0, 30), "F", RiskLevel.InDanger));
		rules.add(new RiskRule(6, new AgeRange(30, 200), null, RiskLevel.InDanger));
		rules.add(new RiskRule(5, new AgeRange(0, 30), "M", RiskLevel.EarlyOnset));
		rules.add(new RiskRule(7, new AgeRange(0, 30), "F", RiskLevel.EarlyOnset));
		rules.add(new RiskRule(8, new AgeRange(30, 200), null, RiskLevel.EarlyOnset));
		
		return rules.stream().sorted().collect(Collectors.toList());
		
	}
}
