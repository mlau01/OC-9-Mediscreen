package com.abernathyclinic.mediscreennote;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.ConstraintViolationException;

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
	
	@Test
	public void CRUDNoteTest() throws NoSuchNoteException {
		//Prepare
		String author = "Laurent Vouaze";
		LocalDateTime created = LocalDateTime.of(2020, 01, 01, 12, 00);
		int patientId = 21;
		String note_content = "Hello world!";
		
		NoteModel note = new NoteModel();
		note.setAuthor(author);
		note.setCreated(created);
		note.setPatientId(patientId);
		note.setNote(note_content);
		
		//Create
		
		NoteModel noteCreated = noteService.create(note);
		assertNotNull(noteCreated.getId());
		
		//Read
		NoteModel noteGetted = noteService.getById(noteCreated.getId());
		
		assertEquals(author, noteGetted.getAuthor());
		assertNotNull(noteGetted.getCreated());
		assertEquals(patientId, noteGetted.getPatientId());
		assertEquals(note_content, noteGetted.getNote());
		
		//Update
		noteGetted.setAuthor("Test");
		noteGetted.setNote("Hello Modified!");
		
		NoteModel noteUpdated = noteService.put(noteGetted);
		
		assertEquals("Test", noteUpdated.getAuthor());
		assertEquals("Hello Modified!", noteUpdated.getNote());
		
		//Delete
		String saveId = noteGetted.getId();
		noteService.delete(saveId);
		
		assertThrows(NoSuchNoteException.class, () -> noteService.getById(saveId));
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
	public void constraintNoteTest() {
		NoteModel note1 = new NoteModel(null, 0,"", null, "Hello World!");
		assertThrows(ConstraintViolationException.class, () -> noteService.create(note1));
		
		NoteModel note3 = new NoteModel(null, 0,"Test", null, "");
		assertThrows(ConstraintViolationException.class, () -> noteService.create(note3));
	}

}
