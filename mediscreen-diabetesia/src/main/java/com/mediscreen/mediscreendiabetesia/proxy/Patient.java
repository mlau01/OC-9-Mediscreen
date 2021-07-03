package com.mediscreen.mediscreendiabetesia.proxy;

import java.time.LocalDate;

import lombok.Data;

@Data
public class Patient {
	
	 Integer id;
	 String firstName;
	 String lastName;
	 LocalDate dateOfBirth;
	 String sex;
	 String phone;
	 String address;
	 String city;

}
