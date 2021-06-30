package com.abernathyclinic.mediscreennote.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abernathyclinic.mediscreennote.exception.NoSuchNoteException;
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
	/**
	 * Create a note
	 * @param note to create
	 * @return NoteModel created if succeed
	 * 30 juin 2021
	 */
	public NoteModel create(NoteModel note) {
		return noteRepository.save(note);
	}

	@Override
	/**
	 * Get a patient note by id
	 * @param id of the note
	 * @return NoteModel
	 * @throw NoSuchNoteException
	 * 30 juin 2021
	 */
	public NoteModel getById(String id) throws NoSuchNoteException {
		Optional<NoteModel> note = noteRepository.findById(id);
		if( ! note.isPresent()) {
			throw new NoSuchNoteException("Note with Id: "+ id + " not found");
		}
		return note.get();
	}

	@Override
	/**
	 * Get a the list of a patient notes ordered by latest date
	 * @param id of the patient
	 * @return List<NoteModel>
	 * @throw NoSuchNoteException when no notes was found for this patient id
	 * 30 juin 2021
	 */
	public List<NoteModel> getByPatientIdOrderedDesc(int id) throws NoSuchNoteException {
		Optional<List<NoteModel>> notes = noteRepository.findByPatientIdOrderByCreatedDesc(id);
		if( ! notes.isPresent()) {
			throw new NoSuchNoteException("No note found this patient id: " + id);
		}
		
		return notes.get();
		
	}

	@Override
	/**
	 * Delete a note by id
	 * @param id
	 * @throw NoSuchNoteException when no note found with this id
	 * 30 juin 2021
	 */
	public void delete(String id) throws NoSuchNoteException {
		if(noteRepository.existsById(id)) {
			noteRepository.deleteById(id);
		} else {
			throw new NoSuchNoteException("No note found for id: " + id);
		}
		
	}

	/**
	 * Update an existing note or create it if the id is not registered
	 * @param note
	 * @return NoteModel updated
	 * 30 juin 2021
	 */
	@Override
	public NoteModel put(NoteModel note) {
		Optional<NoteModel> odb_note = noteRepository.findById(note.getId());
		if( ! odb_note.isPresent()) {
			create(note);
		}
		
		NoteModel db_note = odb_note.get();
		db_note.setAuthor(note.getAuthor());
		db_note.setNote(note.getNote());
		
		
		return noteRepository.save(db_note);
	}
	
	

}
