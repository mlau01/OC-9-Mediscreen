package com.mediscreen.mediscreendiabetesia;

import java.time.LocalDate;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediscreen.mediscreendiabetesia.proxy.Note;
import com.mediscreen.mediscreendiabetesia.proxy.NoteProxy;
import com.mediscreen.mediscreendiabetesia.proxy.Patient;
import com.mediscreen.mediscreendiabetesia.proxy.PatientProxy;
import com.mediscreen.mediscreendiabetesia.service.DiabetesService;

import feign.Feign;
import feign.jackson.JacksonDecoder;

@SpringBootTest
public class DiabetesServiceIT {
	
	@Autowired
	private DiabetesService diabetesService;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Test
	@Disabled //Execute this only one time to add demonstration datas
	public void addDemoPatients(@Value("${patientapi.socket}") String patientApiSocket,
								@Value("${noteapi.socket}") String noteApiSocket) 
						throws JsonProcessingException{
		
		Patient[] patients = {
		new Patient("Lucas", "Fergusen", LocalDate.of(1968, 6, 22), "M", "387-866-1399", "2 Warren Street", null),
		new Patient("Pippa", "Rees", LocalDate.of(1952, 9, 27), "F", "628-423-0993", "745 West Valley Farms Drive", null),
		new Patient("Edward", "Arnold", LocalDate.of(1952, 11, 11), "M", "123-727-2779", "599 East Garden Ave", null),
		new Patient("Anthony", "Sharp", LocalDate.of(1946, 11, 26), "M", "451-761-8383", "894 Hall Street", null),
		new Patient("Wendy", "Ince", LocalDate.of(1958, 6, 29), "F", "802-911-9975", "4 Southampton Road", null),
		new Patient("Tracey", "Ross", LocalDate.of(1949, 12, 7), "F", "131-396-5049", "40 Sulphur Springs Dr", null),
		new Patient("Claire", "Wilson", LocalDate.of(1949, 12, 7), "F", "131-396-5049", "40 Sulphur Springs Dr", null),
		new Patient("Max", "Buckland", LocalDate.of(1945, 6, 24), "M", "833-534-0864", "193 Vale St", null),
		new Patient("Natalie", "Clark", LocalDate.of(1964, 6, 18), "F", "241-467-9197", "12 Beechwood Road", null),
		new Patient("Piers", "Bailey", LocalDate.of(1959, 6, 22), "M", "747-815-0557", "1202 Bumble Dr", null)};
		
		Note[][] notes = {
				{ 
					new Note("Dr Strange", "Le patient déclare qu'il « se sent très bien"),
					new Note("Dr Strange", "Le patient déclare qu'il se sent fatigué pendant la journée"),
					new Note("Dr Strange", "Le patient déclare qu'il ne se sent pas si fatigué que ça")
				},
				{
					new Note("Dr Strange", "Le patient déclare qu'il ressent beaucoup de stress au travail\r\n"),
					new Note("Dr Strange", "Le patient déclare avoir fait une réaction aux médicaments au cours des 3 derniers mois\r\n"),
					new Note("Dr Strange", "Tests de laboratoire indiquant une microalbumine élevée\r\n"),
					new Note("Dr Strange", "Le patient déclare que tout semble aller bien\r\n")
				},
				{
					new Note("Dr Strange", "Le patient déclare qu'il fume depuis peu\r\n"),
					new Note("Dr Strange", "Tests de laboratoire indiquant une microalbumine élevée\r\n"),
					new Note("Dr Strange", "Le patient déclare qu'il est fumeur et qu'il a cessé de fumer l'année dernière\r\n"),
					new Note("Dr Strange", "Tests de laboratoire indiquant un taux de cholestérol LDL élevé\r\n")
				},
				{
					new Note("Dr Strange", "Le patient déclare qu'il lui est devenu difficile de monter les escaliers\r\n"),
					new Note("Dr Strange", "Le patient déclare qu'il a mal au dos lorsqu'il reste assis pendant longtemps\r\n"),
					new Note("Dr Strange", "Le patient déclare avoir commencé à fumer depuis peu\r\n")
				},
				{
					new Note("Dr Strange", "Le patient déclare avoir des douleurs au cou occasionnellement\r\n"),
					new Note("Dr Strange", "Le patient déclare avoir eu plusieurs épisodes de vertige depuis la dernière visite.\r\n"),
					new Note("Dr Strange", "Le patient déclare qu'il souffre encore de douleurs cervicales occasionnelles\r\n"),
					new Note("Dr Strange", "Le patient déclare avoir eu plusieurs épisodes de vertige depuis la dernière visite.\r\n")
				},
				{
					new Note("Dr Strange", "Le patient déclare qu'il se sent bien\r\n"),
					new Note("Dr Strange", "Le patient déclare qu'il se sent bien\r\n")
				},
				{
					new Note("Dr Strange", "Le patient déclare qu'il se réveille souvent avec une raideur articulaire\r\n")
				}, 
				{
					new Note("Dr Strange", "Les tests de laboratoire indiquent que les anticorps sont élevés\r\n")	
				},
				{
					new Note("Dr Strange", "Le patient déclare avoir de la difficulté à se concentrer sur ses devoirs scolaires\r\n"),
					new Note("Dr Strange", "Le patient déclare qu'il s’impatiente facilement en cas d’attente prolongée\r\n"),
					new Note("Dr Strange", "Le patient signale qu'il est facilement irrité par des broutilles\r\n")
				},
				{
					new Note("Dr Strange", "Le patient déclare qu'il n'a aucun problème\r\n"),
					new Note("Dr Strange", "Le patient déclare qu'il n'a aucun problème\r\n"),
					new Note("Dr Strange", "Le patient déclare qu'il n'a aucun problème\r\n"),
					new Note("Dr Strange", "Le patient déclare qu'il n'a aucun problème\r\n")
				}
				
		};
		
		PatientProxy patientProxy = Feign.builder()
				.decoder(new JacksonDecoder())
				.target(PatientProxy.class, patientApiSocket);
		
		NoteProxy noteProxy = Feign.builder()
				.decoder(new JacksonDecoder())
				.target(NoteProxy.class, noteApiSocket);
				
		for(int i = 0; i < patients.length; i++) {
			Patient patientCreated = patientProxy.addPatient(objectMapper.writeValueAsString(patients[i]));
			for(int y = 0; y < notes[i].length; y++) {
				notes[i][y].setPatientId(patientCreated.getId());
				noteProxy.addNote(objectMapper.writeValueAsString(notes[i][y]));
			}
		}
		
	}
	
	@Test
	public void getDiabetesRiskTest_shouldReturnCorrectRiskLevel() {
		//Prepare

		
		
		
		
	}
	
	/*

	 */

}
