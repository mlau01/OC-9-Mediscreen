package com.mediscreen.mediscreendiabetesia.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mediscreen.mediscreendiabetesia.proxy.Note;
import com.mediscreen.mediscreendiabetesia.proxy.NoteProxy;

@Service
public class NoteServiceImpl implements INoteService {
	
	private NoteProxy noteProxy;
	
	@Autowired
	public NoteServiceImpl(NoteProxy p_noteProxy) {
		noteProxy = p_noteProxy;
	}
	
	public List<Note> getAllPatientNotes(int patientId){
		
		return noteProxy.getAllPatientNotes(patientId);
		
	}

}
