package com.mediscreen.mediscreendiabetesia.proxy;

import java.util.List;

import feign.Param;
import feign.RequestLine;

public interface NoteProxy {
	
	@RequestLine("GET /notes/patient/{pid}")
	public List<Note> getAllPatientNotes(@Param("pid") int pid);

}
