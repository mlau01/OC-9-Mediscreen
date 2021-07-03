package com.mediscreen.mediscreendiabetesia;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.mediscreen.mediscreendiabetesia.proxy.Note;
import com.mediscreen.mediscreendiabetesia.proxy.NoteProxy;

public class DiabeteIAModuleTest {
	
	@Test
	public void getNoteProxy_shouldReturnNoteProxyModule() {
		DiabetesIAModule diabetesIaModule = new DiabetesIAModule();
		
		NoteProxy noteProxy = diabetesIaModule.getProxyNote("http://localhost:8082");
		List<Note> notes = noteProxy.getAllPatientNotes(99);
		
		assertNotNull(noteProxy);
		assertNotNull(notes);
		
	}
}
