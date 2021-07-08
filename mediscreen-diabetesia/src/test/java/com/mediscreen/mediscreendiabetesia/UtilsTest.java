package com.mediscreen.mediscreendiabetesia;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.mediscreen.mediscreendiabetesia.utils.AgeRange;
import com.mediscreen.mediscreendiabetesia.utils.RiskLevel;
import com.mediscreen.mediscreendiabetesia.utils.RiskRule;

public class UtilsTest {
	
	@Test
	public void ageRangeTest_shouldSetAndGetCorrectly()
	{
		AgeRange ageRange = new AgeRange();
		ageRange.setStart(10);
		ageRange.setEnd(100);
		
		assertEquals(10, ageRange.getStart());
		assertEquals(100, ageRange.getEnd());
	}
	
	@Test
	public void RiskRuleTest_shouldSetAndGetCorrectly() {
		AgeRange ageRange = new AgeRange(10, 100);
		
		RiskRule riskRule = new RiskRule();
		riskRule.setTriggerLimit(5);
		riskRule.setAgeRange(ageRange);
		riskRule.setSex("M");
		riskRule.setRiskLevel(RiskLevel.Borderline);
		
		assertEquals(5, riskRule.getTriggerLimit());
		assertEquals(ageRange, riskRule.getAgeRange());
		assertEquals("M", riskRule.getSex());
		assertEquals(RiskLevel.Borderline, riskRule.getRiskLevel());
	}

}
