package com.notes.notes.repository;


import com.notes.notes.model.AppUser;
import com.notes.notes.model.NotesModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NotesRepository extends JpaRepository<NotesModel, Long>{
    List<NotesModel> findByAuthorUsername(String username);
    Optional<NotesModel> findByIdAndAuthorUsername(Long id, String username);
    List<NotesModel> findByTitleContainingIgnoreCaseAndAuthor(String title, AppUser author);

    NotesModel save(NotesModel noteModel, AppUser user);
}
