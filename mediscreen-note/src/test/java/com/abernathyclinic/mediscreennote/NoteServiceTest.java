package com.abernathyclinic.mediscreennote;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;

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
		
		String saveId = noteGetted.getId();
		noteService.delete(saveId);
		
		assertThrows(NoSuchNoteException.class, () -> noteService.getById(saveId));
	}
	
	@Test
	public void getPatientNote_shouldReturnOrderedByCreationNotes() throws NoSuchNoteException {
		NoteModel note1 = new NoteModel();
		note1.setAuthor("AZERTY");
		note1.setCreated(LocalDate.of(2012, 01, 01));
		note1.setPatientId(1);
		note1.setNote("Hello One !");
		
		NoteModel note2 = new NoteModel();
		note2.setAuthor("QSDFGH");
		note2.setCreated(LocalDate.of(2010, 01, 01));
		note2.setPatientId(1);
		note2.setNote("Hello Two !");
		
		String id1 = noteService.create(note1).getId();
		String id2 = noteService.create(note2).getId();
		
		List<NoteModel> notes = noteService.getByPatientIdOrderedDesc(1);
		assertNotEquals(0, notes.size());

		//Assert that the current note creation date of the iteration is always greater than the previous
		NoteModel previous = null;
		for(NoteModel note : notes) {
			if(previous == null) {
				previous = note;
			} else {
				assertTrue( (note.getCreated().compareTo(previous.getCreated())) <= 0);
				previous = note;
			}
		}
		
		
		noteService.delete(id1);
		noteService.delete(id2);
	}

}
