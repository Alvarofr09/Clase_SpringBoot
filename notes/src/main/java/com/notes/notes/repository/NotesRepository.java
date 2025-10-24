package com.notes.notes.repository;


import com.notes.notes.model.NotesModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotesRepository extends JpaRepository<NotesModel, Long>{

}
