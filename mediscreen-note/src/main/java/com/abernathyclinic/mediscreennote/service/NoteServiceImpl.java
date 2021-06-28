package com.abernathyclinic.mediscreennote.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abernathyclinic.mediscreennote.exception.NoSuchNoteException;
import com.abernathyclinic.mediscreennote.exception.NoSuchPatientException;
import com.abernathyclinic.mediscreennote.model.NoteModel;
import com.abernathyclinic.mediscreennote.repository.NoteRepository;

@Service
public class NoteServiceImpl implements INoteService {

	private NoteRepository noteRepository;
	
	@Autowired
	NoteServiceImpl(NoteRepository p_noteRepository) {
		noteRepository = p_noteRepository;
	}
	
	@Override
	public NoteModel create(NoteModel note) {
		return noteRepository.save(note);
	}

	@Override
	public NoteModel getById(String id) throws NoSuchNoteException {
		Optional<NoteModel> note = noteRepository.findById(id);
		if( ! note.isPresent()) {
			throw new NoSuchNoteException("Note with Id: "+ id + " not found");
		}
		return note.get();
	}

	@Override
	public List<NoteModel> getByPatientId(String id) throws NoSuchPatientException {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
