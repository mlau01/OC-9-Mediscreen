package com.abernathyclinic.mediscreennote;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.abernathyclinic.mediscreennote.exception.NoSuchNoteException;
import com.abernathyclinic.mediscreennote.model.NoteModel;
import com.abernathyclinic.mediscreennote.service.INoteService;

@SpringBootTest
public class NoteServiceTest {
	
	@Autowired
	private INoteService noteService;
	
	private static NoteModel noteTest;
	
	@BeforeAll
	public static void setUp(){
		String author = "Laurent Vouaze";
		LocalDateTime created = LocalDateTime.of(2020, 01, 01, 12, 00);
		int patientId = 21;
		String note_content = "Hello world!";
		
		noteTest = new NoteModel();
		noteTest.setAuthor(author);
		noteTest.setCreated(created);
		noteTest.setPatientId(patientId);
		noteTest.setNote(note_content);
	}
	
	@Test
	public void createNoteTest_shouldCreateNoteCorrectly() throws NoSuchNoteException {
		NoteModel noteCreated = noteService.create(noteTest);
		
		assertNotNull(noteCreated.getId());
		assertEquals(noteTest.getAuthor(), noteCreated.getAuthor());
		assertNotNull(noteCreated.getCreated());
		assertEquals(noteTest.getPatientId(), noteCreated.getPatientId());
		assertEquals(noteTest.getNote(), noteCreated.getNote());
		
		//Clean up
		String id = noteCreated.getId();
		noteService.delete(noteCreated.getId());
		assertThrows(NoSuchNoteException.class, () -> noteService.getById(id));
	}
	
	@Test
	public void readNoteTest_shouldReadNoteCorrectly() throws NoSuchNoteException {
		NoteModel noteCreated = noteService.create(noteTest);
		
		NoteModel noteGetted = noteService.getById(noteCreated.getId());
		
		assertNotNull(noteGetted.getId());
		assertEquals(noteTest.getAuthor(), noteGetted.getAuthor());
		assertNotNull(noteGetted.getCreated());
		assertEquals(noteTest.getPatientId(), noteGetted.getPatientId());
		assertEquals(noteTest.getNote(), noteGetted.getNote());
		
		//Clean up
		String id = noteCreated.getId();
		noteService.delete(noteCreated.getId());
		assertThrows(NoSuchNoteException.class, () -> noteService.getById(id));
	}
	
	@Test
	public void updateNoteTest_shouldUpdateNoteCorrectly() {
		NoteModel noteCreated = noteService.create(noteTest);
		noteCreated.setAuthor("Test");
		noteCreated.setNote("Hello Modified!");
		
		NoteModel noteUpdated = noteService.put(noteCreated);
		
		assertEquals("Test", noteUpdated.getAuthor());
		assertEquals("Hello Modified!", noteUpdated.getNote());
	}
	
	@Test
	public void getPatientNote_shouldReturnListOfNotes() throws NoSuchNoteException {
		NoteModel note1 = new NoteModel();
		note1.setAuthor("AZERTY");
		note1.setPatientId(1);
		note1.setNote("Hello One !");
		
		NoteModel note2 = new NoteModel();
		note2.setAuthor("QSDFGH");
		note2.setPatientId(1);
		note2.setNote("Hello Two !");
		
		String id1 = noteService.create(note1).getId();
		String id2 = noteService.create(note2).getId();
		
		List<NoteModel> notes = noteService.getByPatientIdOrderedDesc(1);
		assertEquals(2, notes.size());
		
		noteService.delete(id1);
		noteService.delete(id2);
	}
	
	@Test
	public void getUnknownPatientNotes_shouldThrowException() {
		assertThrows(NoSuchNoteException.class, () -> noteService.getByPatientIdOrderedDesc(934));
	}
	
	@Test
	public void constraintNoteTest() {
		NoteModel note1 = new NoteModel(null, 0,"", null, "Hello World!");
		assertThrows(ConstraintViolationException.class, () -> noteService.create(note1));
		
		NoteModel note3 = new NoteModel(null, 0,"Test", null, "");
		assertThrows(ConstraintViolationException.class, () -> noteService.create(note3));
	}

}
