package com.mediscreen.mediscreendiabetesia.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RiskRule implements Comparable<RiskRule> {
	private int triggerLimit;
	private AgeRange ageRange;
	private String sex;
	private RiskLevel riskLevel;

	@Override
	/**
	 * Compare RiskRule by triggerLimit
	 * Used to sort map used by DiabetesService
	 * That organize the map by triggerLimit desc
	 * Never return 0, if 0(triggerLimit equality) return 1
	 * @param RiskRule
	 * @return 
	 * 8 juil. 2021
	 */
	public int compareTo(RiskRule o) {
		int compareResult = Integer.compare(this.triggerLimit, o.triggerLimit) * - 1;
		if(compareResult == 0) {
			compareResult = 1;
		}
		return compareResult;
	}
	
	@Override
	public String toString() {
		return  triggerLimit + ", " + ageRange + ", " + sex + ", " + riskLevel;
	}
}
