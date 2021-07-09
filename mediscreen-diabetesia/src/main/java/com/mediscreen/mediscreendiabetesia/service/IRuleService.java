package com.mediscreen.mediscreendiabetesia.service;

import java.util.List;

import com.mediscreen.mediscreendiabetesia.utils.RiskRule;

public interface IRuleService {

	/**
	 * Should provide a list of rules used to determine the patient diabetes risk
	 * 9 juil. 2021
	 */
	public List<RiskRule> getRules();
	
}
