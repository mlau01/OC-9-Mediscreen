package com.abernathyclinic.mediscreennote.model;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NoteModel {
	

	@Id
	private String id;
	
	@NotNull
	private Integer patientId;
	
	@NotBlank
	@Length(max = 20, message = "max 20 chars")
	private String author;

	private LocalDateTime created;
	
	@NotBlank
	private String note;
}
