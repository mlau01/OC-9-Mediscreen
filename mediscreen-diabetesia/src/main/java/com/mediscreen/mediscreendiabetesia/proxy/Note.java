package com.mediscreen.mediscreendiabetesia.proxy;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Note {
	
	private String id;
	private Integer patientId;
	private String author;
	
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime created;
	private String note;
	
	public Note(String author, String note) {
		this.note = note;
		this.author = author;
	}
	
	

}
