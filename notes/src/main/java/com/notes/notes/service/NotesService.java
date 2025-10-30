package com.notes.notes.service;


import com.notes.notes.exception.UnauthorizedAccessException;
import com.notes.notes.model.AppUser;
import com.notes.notes.model.NotesModel;
import com.notes.notes.repository.AppUserRepository;
import com.notes.notes.repository.NotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class NotesService {
    private final NotesRepository noteRepository;
    private final AppUserRepository appUserRepository;

    @Autowired
    public NotesService(NotesRepository noteRepository, AppUserRepository appUserRepository) {
        this.noteRepository = noteRepository;
        this.appUserRepository = appUserRepository;
    }

    // Get all notes
    public List<NotesModel> getAllNotesByUsername(Principal principal) {
        String username = principal.getName();
        return noteRepository.findByAuthorUsername(username);
    }

    // Get note by id
    public Optional<NotesModel> getNoteById(long id) {
        return noteRepository.findById(id);
    }

    // Get note by Title and user
    public List<NotesModel> getNoteByTitleAndAuthor(String title, Principal principal) {
        AppUser user = appUserRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        return noteRepository.findByTitleAndAuthor(title, user);
    }

    public List<NotesModel> getNoteByTitleContainingAndAuthor(String title, Principal principal) {
        AppUser user = appUserRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        return noteRepository.findByTitleContainingIgnoreCaseAndAuthor(title, user);
    }

    // Create a new note
    public NotesModel createNewNote(NotesModel noteModel, Principal principal) {
        // Buscar el usuario
        AppUser user = appUserRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        // Crear nueva nota
        NotesModel note = new NotesModel();
        note.setTitle(noteModel.getTitle());
        note.setDescription(noteModel.getDescription());
        note.setComplete(noteModel.isComplete());
        note.setAuthor(user);


        return noteRepository.save(note);
    }

    // Delete a note
    public void deleteNote(long id) {
        noteRepository.deleteById(id);
    }

    // Update an existing note
    public NotesModel updateNote(NotesModel noteModel, Principal principal){
        NotesModel existingNote = noteRepository.findById(noteModel.getId())
                .orElseThrow(() -> new NoSuchElementException("Note not found with ID: " + noteModel.getId()));

        if (!existingNote.getAuthor().getUsername().equals(principal.getName())) {
            throw new UnauthorizedAccessException("No tienes permiso para editar esta nota");
        }

        existingNote.setTitle(noteModel.getTitle());
        existingNote.setDescription(noteModel.getDescription());
        existingNote.setComplete(noteModel.isComplete());

        return noteRepository.save(existingNote);
    }


}