package com.mediscreen.mediscreendiabetesia.proxy;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Note {
	
	private String id;
	private Integer patientId;
	private String author;
	private LocalDateTime created;
	private String note;

}
