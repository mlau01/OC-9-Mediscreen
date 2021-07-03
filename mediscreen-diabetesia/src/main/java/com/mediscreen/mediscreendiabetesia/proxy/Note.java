package com.mediscreen.mediscreendiabetesia.proxy;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Note {
	
	public String id;
	public Integer patientId;
	public String author;
	public LocalDateTime created;
	public String note;

}
