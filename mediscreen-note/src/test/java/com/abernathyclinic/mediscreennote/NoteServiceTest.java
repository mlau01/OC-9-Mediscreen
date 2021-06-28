package com.abernathyclinic.mediscreennote;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

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
	public void createReadNoteTest() throws NoSuchNoteException {
		
		String author = "Laurent Vouaze";
		LocalDate created = LocalDate.now();
		int patientId = 21;
		String note_content = "Hello world!";
		
		NoteModel note = new NoteModel();
		note.setAuthor(author);
		note.setCreated(created);
		note.setPatientId(patientId);
		note.setNote(note_content);
		
		NoteModel noteCreated = noteService.create(note);
		assertNotNull(noteCreated.getId());
		
		NoteModel noteGetted = noteService.getById(noteCreated.getId());
		
		assertEquals(author, noteGetted.getAuthor());
		assertEquals(created, noteGetted.getCreated());
		assertEquals(patientId, noteGetted.getPatientId());
		assertEquals(note_content, noteGetted.getNote());
	}
	
	@Test
	public void getNullNoteTest_shouldThrowException() {
		assertThrows(NoSuchNoteException.class, () -> noteService.getById("0"));
	}

}
