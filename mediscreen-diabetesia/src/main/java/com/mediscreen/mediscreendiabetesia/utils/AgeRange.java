package com.mediscreen.mediscreendiabetesia.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
/**
 * Used to represent an age range
 * 8 juil. 2021
 */
public class AgeRange {
	private int start;
	private int end;	
	
	@Override
	public String toString() {
		return start + ", " + end;
	}
	
}
