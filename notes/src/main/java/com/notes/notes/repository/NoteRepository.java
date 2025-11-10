package com.notes.notes.repository;


import com.notes.notes.model.AppUserModel;
import com.notes.notes.model.NoteModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NoteRepository extends JpaRepository<NoteModel, Long>{
    List<NoteModel> findByAuthorUsername(String username);
    Optional<NoteModel> findByIdAndAuthorUsername(Long id, String username);
    List<NoteModel> findByTitleContainingIgnoreCaseAndAuthor(String title, AppUserModel author);

}
