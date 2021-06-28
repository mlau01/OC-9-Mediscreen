package com.abernathyclinic.mediscreennote.model;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Length;

import lombok.Data;
import lombok.NoArgsConstructor;

@Document
@Data
@NoArgsConstructor
public class NoteModel {
	
	@Id
	private String id;
	
	@NotNull
	private Integer patientId;
	
	@NotBlank
	@Length(max = 20, message = "max 20 chars")
	private String author;
	
	@Past
	@NotNull
	private LocalDate created;
	
	@NotBlank
	@Length(max = 1000, message = "max 1000 chars")
	private String note;
}
