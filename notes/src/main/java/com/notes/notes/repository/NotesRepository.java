package com.notes.notes.repository;


import com.notes.notes.model.AppUser;
import com.notes.notes.model.NotesModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotesRepository extends JpaRepository<NotesModel, Long>{
    List<NotesModel> findByAuthorUsername(String username);
    List<NotesModel> findByTitleAndAuthor(String title, AppUser author);
    List<NotesModel> findByTitleContainingIgnoreCaseAndAuthor(String title, AppUser author);

}
