package com.mediscreen.mediscreendiabetesia.proxy;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Patient {
	
	 private Integer id;
	 private String firstName;
	 private String lastName;
	 private LocalDate dateOfBirth;
	 private String sex;
	 private String phone;
	 private String address;
	 private String city;
	 
	public Patient(LocalDate dateOfBirth, String sex) {
		this.dateOfBirth = dateOfBirth;
		this.sex = sex;
	}
	 
	 
}
