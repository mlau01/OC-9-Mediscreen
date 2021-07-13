package com.mediscreen.mediscreendiabetesia.proxy;

import java.time.LocalDate;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Patient {
	
	 private Integer id;
	 private String firstName;
	 private String lastName;
	 
	 @JsonDeserialize(using = LocalDateDeserializer.class)
	 private LocalDate dateOfBirth;
	 private String sex;
	 private String phone;
	 private String address;
	 private String city;
	 
	public Patient(Integer id, LocalDate dateOfBirth, String sex) {
		this.id = id;
		this.dateOfBirth = dateOfBirth;
		this.sex = sex;
	}
	
	public Patient(String firstName,
			String lastName,LocalDate dateOfBirth,
			String sex,
			String phone,
			 String address,
			String city) {
		
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.sex = sex;
		this.phone = phone;
		this.address = address;
		this.city = city;
	}
	 
	 
}
