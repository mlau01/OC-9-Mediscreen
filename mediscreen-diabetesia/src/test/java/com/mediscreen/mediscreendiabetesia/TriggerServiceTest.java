package com.mediscreen.mediscreendiabetesia;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.mediscreen.mediscreendiabetesia.service.TriggerService;

public class TriggerServiceTest {
	
	
	
	@Test
	public void getTriggersTest_shouldReturnTriggerList() {
		TriggerService triggerService = new TriggerService();
		
		List<String> triggers = triggerService.getTriggers();
		
		assertTrue(triggers.size() > 0);
	}

}
