package com.abernathyclinic.mediscreennote.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.abernathyclinic.mediscreennote.model.NoteModel;

@Repository
public interface NoteRepository extends MongoRepository<NoteModel, String> {
	
	Optional<List<NoteModel>> findByPatientIdOrderByCreatedDesc(int id);

}
