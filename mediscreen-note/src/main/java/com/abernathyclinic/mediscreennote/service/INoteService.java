package com.abernathyclinic.mediscreennote.service;

import java.util.List;

import com.abernathyclinic.mediscreennote.exception.NoSuchNoteException;
import com.abernathyclinic.mediscreennote.exception.NoSuchPatientException;
import com.abernathyclinic.mediscreennote.model.NoteModel;

public interface INoteService {

	NoteModel create(NoteModel note);

	List<NoteModel> getByPatientId(String id) throws NoSuchPatientException;
	
	NoteModel getById(String id) throws NoSuchNoteException;

}
