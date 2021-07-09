package com.mediscreen.mediscreendiabetesia;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.mediscreen.mediscreendiabetesia.service.RuleServiceImpl;
import com.mediscreen.mediscreendiabetesia.utils.RiskRule;

public class RuleServiceTest {
	
	@Test
	public void getRulesTest_shouldReturnOrderingRulesByTriggerLimitDesc() {
		RuleServiceImpl ruleService = new RuleServiceImpl();
		List<RiskRule> rules = ruleService.getRules();
		
		Iterator<RiskRule> it = rules.iterator();
		RiskRule previous = null;
		while(it.hasNext()) {
			if(previous == null) {
				previous = it.next();
			}
			else {
				RiskRule current = it.next();
				assertTrue(current.getTriggerLimit() <= previous.getTriggerLimit());
				previous = current;
			}
		}
	}

}
