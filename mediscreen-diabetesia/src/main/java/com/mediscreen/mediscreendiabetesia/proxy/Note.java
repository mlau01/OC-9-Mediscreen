package com.mediscreen.mediscreendiabetesia.proxy;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Note {
	
	private String id;
	private Integer patientId;
	private String author;
	
	@JsonDeserialize(using = LocalDateDeserializer.class)
	private LocalDateTime created;
	private String note;
	
	public Note(String author, String note) {
		this.note = note;
		this.author = author;
	}
	
	

}
