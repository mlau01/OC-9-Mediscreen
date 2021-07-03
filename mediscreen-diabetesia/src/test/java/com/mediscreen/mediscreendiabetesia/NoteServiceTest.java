package com.mediscreen.mediscreendiabetesia;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;

import com.mediscreen.mediscreendiabetesia.proxy.NoteProxy;
import com.mediscreen.mediscreendiabetesia.service.NoteService;

@ExtendWith(MockitoExtension.class)
public class NoteServiceTest {
	
	@Mock
	NoteProxy noteProxy;

	@Test
	public void getPatientNoteTest_shouldCallNoteProxy() {
		NoteService noteService = new NoteService(noteProxy);
		
		noteService.getAllPatientNotes(99);
		
		verify(noteProxy, Mockito.times(1)).getAllPatientNotes(99);
	}
}
