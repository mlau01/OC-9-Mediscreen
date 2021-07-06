package com.mediscreen.mediscreendiabetesia.proxy;

import java.util.List;

import feign.Headers;
import feign.Param;
import feign.RequestLine;

public interface NoteProxy {
	
	@RequestLine("GET /notes/patient/{pid}")
	public List<Note> getAllPatientNotes(@Param("pid") int pid);
	
	@RequestLine("POST /notes")
	@Headers("Content-Type: application/json;charset=UTF-8")
	void addNote(String string);

}
