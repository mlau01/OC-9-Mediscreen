package com.mediscreen.mediscreendiabetesia.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.mediscreen.mediscreendiabetesia.utils.RiskLevel;

@Data
@NoArgsConstructor
public class PatientAssessDto {
	
	private String firstName;
	private String lastName;
	private int age;
	private RiskLevel riskLevel;

}
