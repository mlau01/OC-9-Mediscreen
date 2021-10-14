package com.mediscreen.mediscreendiabetesia;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediscreen.mediscreendiabetesia.dto.PatientAssessDto;
import com.mediscreen.mediscreendiabetesia.exception.NoSuchPatientException;
import com.mediscreen.mediscreendiabetesia.model.PatientTestModel;
import com.mediscreen.mediscreendiabetesia.proxy.Note;
import com.mediscreen.mediscreendiabetesia.proxy.NoteProxy;
import com.mediscreen.mediscreendiabetesia.proxy.Patient;
import com.mediscreen.mediscreendiabetesia.proxy.PatientProxy;
import com.mediscreen.mediscreendiabetesia.service.DiabetesServiceImpl;
import com.mediscreen.mediscreendiabetesia.utils.RiskLevel;

@SpringBootTest(properties = { "patientapi.socket=http://localhost:8081", "noteapi.socket=http://localhost:8082" })
public class DiabetesServiceIT {
	
	@Autowired
	private DiabetesServiceImpl diabetesService;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private NoteProxy noteProxy;
	
	@Autowired
	private PatientProxy patientProxy;
	
	
	@Test
	public void diabetesAssessTest_shouldAddTestDatasAndGetCorrectDiabetesAssess() throws JsonProcessingException, NoSuchPatientException {
		
		// Prepare datas
		Map<PatientTestModel, Patient> patientMap = new Hashtable<PatientTestModel, Patient>();
		List<Note> noteCreated = new ArrayList<Note>();
		List<PatientTestModel> patients = new ArrayList<PatientTestModel>();
		
		Note[] noteFergusen =  { 
				new Note("Dr Strange", "Le patient déclare qu'il « se sent très bien »\r\n"
						+ "Poids égal ou inférieur au poids recommandé"),
				new Note("Dr Strange", "Le patient déclare qu'il se sent fatigué pendant la journée\r\n"
						+ "Il se plaint également de douleurs musculaires\r\n"
						+ "Tests de laboratoire indiquant une microalbumine élevée"),
				new Note("Dr Strange", "Le patient déclare qu'il ne se sent pas si fatigué que ça\r\n"
						+ "Fumeur, il a arrêté dans les 12 mois précédents\r\n"
						+ "Tests de laboratoire indiquant que les anticorps sont élevés")
			};
		
		patients.add(new PatientTestModel(
				"Fergusen",
				new Patient("Lucas", "Fergusen", LocalDate.of(1968, 6, 22), "M", "387-866-1399", "2 Warren Street", null),
				noteFergusen, 
				RiskLevel.Borderline));
		
		
		Note[] noteRees =  { 
				new Note("Dr Strange", "Le patient déclare qu'il ressent beaucoup de stress au travail\r\n"
						+ "Il se plaint également que son audition est anormale dernièrement"),
				new Note("Dr Strange", "Le patient déclare avoir fait une réaction aux médicaments au cours des 3 derniers mois\r\n"
						+ "Il remarque également que son audition continue d'être anormale"),
				new Note("Dr Strange", "Tests de laboratoire indiquant une microalbumine élevée"),
				new Note("Dr Strange", "Le patient déclare que tout semble aller bien\r\n"
						+ "Le laboratoire rapporte que l'hémoglobine A1C dépasse le niveau recommandé\r\n"
						+ "Le patient déclare qu’il fume depuis longtemps")
			};
		patients.add(new PatientTestModel(
				"Rees",
				new Patient("Pippa", "Rees", LocalDate.of(1952, 9, 27), "F", "628-423-0993", "745 West Valley Farms Drive", null),
				noteRees, 
				RiskLevel.Borderline));
		
		Note[] noteArnold = {
				new Note("Dr Strange", "Le patient déclare qu'il fume depuis peu"),
				new Note("Dr Strange", "Tests de laboratoire indiquant une microalbumine élevée"),
				new Note("Dr Strange", "Le patient déclare qu'il est fumeur et qu'il a cessé de fumer l'année dernière\r\n"
						+ "Il se plaint également de crises d’apnée respiratoire anormales\r\n"
						+ "Tests de laboratoire indiquant un taux de cholestérol LDL élevé"),
				new Note("Dr Strange", "Tests de laboratoire indiquant un taux de cholestérol LDL élevé")
			};
		
		patients.add(new PatientTestModel(
				"Arnold",
				new Patient("Edward", "Arnold", LocalDate.of(1952, 11, 11), "M", "123-727-2779", "599 East Garden Ave", null),
				noteArnold, 
				RiskLevel.Borderline));
		
		Note[] noteSharp = {
				new Note("Dr Strange", "Le patient déclare qu'il lui est devenu difficile de monter les escaliers\r\n"
						+ "Il se plaint également d’être essoufflé\r\n"
						+ "Tests de laboratoire indiquant que les anticorps sont élevés\r\n"
						+ "Réaction aux médicaments"),
				new Note("Dr Strange", "Le patient déclare qu'il a mal au dos lorsqu'il reste assis pendant longtemps"),
				new Note("Dr Strange", "Le patient déclare avoir commencé à fumer depuis peu\r\n"
						+ "Hémoglobine A1C supérieure au niveau recommandé")
			};
		patients.add(new PatientTestModel(
				"Sharp",
				new Patient("Anthony", "Sharp", LocalDate.of(1946, 11, 26), "M", "451-761-8383", "894 Hall Street", null),
				noteSharp, 
				RiskLevel.Borderline));
		
		
		Note[] noteInce = {
				new Note("Dr Strange", "Le patient déclare avoir des douleurs au cou occasionnellement\r\n"
						+ "Le patient remarque également que certains aliments ont un goût différent\r\n"
						+ "Réaction apparente aux médicaments\r\n"
						+ "Poids corporel supérieur au poids recommandé"),
				new Note("Dr Strange", "Le patient déclare avoir eu plusieurs épisodes de vertige depuis la dernière visite.\r\n"
						+ "Taille incluse dans la fourchette concernée"),
				new Note("Dr Strange", "Le patient déclare qu'il souffre encore de douleurs cervicales occasionnelles\r\n"
						+ "Tests de laboratoire indiquant une microalbumine élevée\r\n"
						+ "Fumeur, il a arrêté dans les 12 mois précédents"),
				new Note("Dr Strange", "Le patient déclare avoir eu plusieurs épisodes de vertige depuis la dernière visite.\r\n"
						+ "Tests de laboratoire indiquant que les anticorps sont élevés")
			};
		
		patients.add(new PatientTestModel(
				"Ince",
				new Patient("Wendy", "Ince", LocalDate.of(1958, 6, 29), "F", "802-911-9975", "4 Southampton Road", null),
				noteInce, 
				RiskLevel.EarlyOnset));
		
		Note[] noteRoss = 	{
				new Note("Dr Strange", "Le patient déclare qu'il se sent bien\r\n"
						+ "Poids corporel supérieur au poids recommandé"),
				new Note("Dr Strange", "Le patient déclare qu'il se sent bien\r\n")
			};
		
		patients.add(new PatientTestModel(
				"Ross",
				new Patient("Tracey", "Ross", LocalDate.of(1949, 12, 7), "F", "131-396-5049", "40 Sulphur Springs Dr", null),
				noteRoss, 
				RiskLevel.None));
		
		
		Note[] noteWilson = {
				new Note("Dr Strange", "Le patient déclare qu'il se réveille souvent avec une raideur articulaire\r\n"
						+ "Il se plaint également de difficultés pour s’endormir\r\n"
						+ "Poids corporel supérieur au poids recommandé\r\n"
						+ "Tests de laboratoire indiquant un taux de cholestérol LDL élevé")
			};
		
		patients.add(new PatientTestModel(
				"Wilson",
				new Patient("Claire", "Wilson", LocalDate.of(1949, 12, 7), "F", "131-396-5049", "40 Sulphur Springs Dr", null),
				noteWilson, 
				RiskLevel.Borderline));
		
		Note[] noteBuckland = {
				new Note("Dr Strange", "Les tests de laboratoire indiquent que les anticorps sont élevés\r\n"
						+ "Hémoglobine A1C supérieure au niveau recommandé")	
			};
		
		patients.add(new PatientTestModel(
				"Buckland",
				new Patient("Max", "Buckland", LocalDate.of(1945, 6, 24), "M", "833-534-0864", "193 Vale St", null),
				noteBuckland, 
				RiskLevel.Borderline));
		
		Note[] noteClark = {
				new Note("Dr Strange", "Le patient déclare avoir de la difficulté à se concentrer sur ses devoirs scolaires\r\n"
						+ "Hémoglobine A1C supérieure au niveau recommandé"),
				new Note("Dr Strange", "Le patient déclare qu'il s’impatiente facilement en cas d’attente prolongée\r\n"
						+ "Il signale également que les produits du distributeur automatique ne sont pas bons\r\n"
						+ "Tests de laboratoire signalant des taux anormaux de cellules sanguines"),
				new Note("Dr Strange", "Le patient signale qu'il est facilement irrité par des broutilles\r\n"
						+ "Il déclare également que l'aspirateur des voisins fait trop de bruit\r\n"
						+ "Tests de laboratoire indiquant que les anticorps sont élevés")
			};
		
		patients.add(new PatientTestModel(
				"Clark",
				new Patient("Natalie", "Clark", LocalDate.of(1964, 6, 18), "F", "241-467-9197", "12 Beechwood Road", null),
				noteClark, 
				RiskLevel.Borderline));
		
		Note[] noteBailey = {
				new Note("Dr Strange", "Le patient déclare qu'il n'a aucun problème"),
				new Note("Dr Strange", "Le patient déclare qu'il n'a aucun problème\r\n"
						+ "Taille incluse dans la fourchette concernée\r\n"
						+ "Hémoglobine A1C supérieure au niveau recommandé"),
				new Note("Dr Strange", "Le patient déclare qu'il n'a aucun problème\r\n"
						+ "Poids corporel supérieur au poids recommandé\r\n"
						+ "Le patient a signalé plusieurs épisodes de vertige depuis sa dernière visite"),
				new Note("Dr Strange", "Le patient déclare qu'il n'a aucun problème\r\n"
						+ "Tests de laboratoire indiquant une microalbumine élevée")
			};
		
		patients.add(new PatientTestModel(
				"Bailey",
				new Patient("Piers", "Bailey", LocalDate.of(1959, 6, 22), "M", "747-815-0557", "1202 Bumble Dr", null),
				noteBailey, 
				RiskLevel.Borderline));
		
		// Add datas
		for(PatientTestModel pt : patients) {
			Patient patient = patientProxy.addPatient(objectMapper.writeValueAsString(pt.getPatient()));
			patientMap.put(pt, patient);
			Note[] pNotes = pt.getNotes();
			for(int i = 0; i < pNotes.length; i++) {
				pNotes[i].setPatientId(patient.getId());
				Note note = noteProxy.addNote(objectMapper.writeValueAsString(pNotes[i]));
				noteCreated.add(note);
			}
		}
		
		// Assert
		
		patientMap.forEach((patientTestModel, patient) -> {
			PatientAssessDto patientAssessDto = null;
			try {
				patientAssessDto = diabetesService.getPatientAssessById(String.valueOf(patient.getId()));
			} catch (NoSuchPatientException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
			
			assertEquals(patientTestModel.getPatient().getFirstName(), patientAssessDto.getFirstName());
			assertEquals(patientTestModel.getPatient().getLastName(), patientAssessDto.getLastName());
			assertEquals(patientTestModel.getRiskLevel(), patientAssessDto.getRiskLevel());
			assertTrue(patientAssessDto.getAge() >= 0);
		});
		
		
		// Clean up
		for(Patient patient : patientMap.values()) {
			patientProxy.deletePatient(String.valueOf(patient.getId()));
		}
		for(Note note : noteCreated) {
			noteProxy.deleteNote(note.getId());
		}
		
	}
}
