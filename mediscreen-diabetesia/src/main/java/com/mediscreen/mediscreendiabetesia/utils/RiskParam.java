package com.mediscreen.mediscreendiabetesia.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RiskParam implements Comparable<RiskParam> {
	private int triggerLimit;
	private AgeRange ageRange;
	private String sex;
	private RiskLevel riskLevel;

	@Override
	public int compareTo(RiskParam o) {
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
