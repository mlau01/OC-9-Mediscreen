package com.mediscreen.mediscreendiabetesia.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.mediscreen.mediscreendiabetesia.utils.AgeRange;
import com.mediscreen.mediscreendiabetesia.utils.RiskLevel;
import com.mediscreen.mediscreendiabetesia.utils.RiskRule;

@Service
public class RuleServiceImpl implements IRuleService{
	
	/**
	 * Return a sorted map by RiskParam.triggerLimit descendant
	 * @return SortedMap<RiskParam, RiskLevel>
	 * 4 juil. 2021
	 */
	public List<RiskRule> getRules() {
		List<RiskRule> rules = new ArrayList<RiskRule>();
		rules.add(new RiskRule(2, new AgeRange(30, 200), null, RiskLevel.Borderline));
		rules.add(new RiskRule(3, new AgeRange(0, 30), "M", RiskLevel.InDanger));
		rules.add(new RiskRule(4, new AgeRange(0, 30), "F", RiskLevel.InDanger));
		rules.add(new RiskRule(6, new AgeRange(30, 200), null, RiskLevel.InDanger));
		rules.add(new RiskRule(5, new AgeRange(0, 30), "M", RiskLevel.EarlyOnset));
		rules.add(new RiskRule(7, new AgeRange(0, 30), "F", RiskLevel.EarlyOnset));
		rules.add(new RiskRule(8, new AgeRange(30, 200), null, RiskLevel.EarlyOnset));
		
		return rules.stream().sorted().collect(Collectors.toList());
		
	}

}
