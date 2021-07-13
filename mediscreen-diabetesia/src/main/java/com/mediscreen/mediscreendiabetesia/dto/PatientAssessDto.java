package com.mediscreen.mediscreendiabetesia.dto;

import com.mediscreen.mediscreendiabetesia.utils.RiskLevel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PatientAssessDto {
	
	private String firstName;
	private String lastName;
	private int age;
	private RiskLevel riskLevel;

}
