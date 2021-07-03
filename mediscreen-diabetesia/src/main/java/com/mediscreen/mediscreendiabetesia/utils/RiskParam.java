package com.mediscreen.mediscreendiabetesia.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RiskParam {
	private int triggerLimit;
	private AgeRange ageRange;
	private String sex;
}
