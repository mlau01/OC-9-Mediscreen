package com.mediscreen.mediscreendiabetesia;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.mediscreen.mediscreendiabetesia.proxy.Note;
import com.mediscreen.mediscreendiabetesia.service.TriggerService;

public class TriggerServiceTest {
	
	
	
	@Test
	public void getTriggersTest_shouldReturnTriggerList() {
		TriggerService triggerService = new TriggerService();
		
		List<String> triggers = triggerService.getTriggers();
		
		assertTrue(triggers.size() > 0);
	}
	
	@Test
	public void getTriggersCount_shouldReturnCorrectNumber() {
		TriggerService triggerService = new TriggerService();
		Note noteWithAllTrigger = new Note();
		noteWithAllTrigger.setNote("Hémoglobine A1CMicroalbumineTaillePoidsFumeurAnormalCholestérolVertigeRechuteRéactionAnticorps");
		List<Note> notes = new ArrayList<Note>();
		notes.add(noteWithAllTrigger);
		notes.add(noteWithAllTrigger);
		
		assertEquals(22, triggerService.getTriggerCount(notes));
	}
	
	

}
