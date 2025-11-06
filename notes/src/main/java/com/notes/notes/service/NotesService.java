package com.notes.notes.service;


import com.notes.notes.exception.UnauthorizedAccessException;
import com.notes.notes.model.AppUser;
import com.notes.notes.model.NotesModel;
import com.notes.notes.repository.AppUserRepository;
import com.notes.notes.repository.NotesRepository;
import com.notes.notes.utils.SecurityUtils;
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
    public List<NotesModel> findAllForCurrentUser() {
        String username = getAuthenticatedUsername();
        return noteRepository.findByAuthorUsername(username);
    }

    // Get note by id
    public Optional<NotesModel> findByIdFor(long id) {
        String username = getAuthenticatedUsername();
        return noteRepository.findByIdAndAuthorUsername(id, username);
    }

    // Create a new note
    public NotesModel createFor(NotesModel noteModel) {
        // Buscar el usuario
        String username = getAuthenticatedUsername();
        AppUser user = appUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        noteModel.setAuthor(user);

        return noteRepository.save(noteModel);
    }

    // Delete a note
    public void deleteNote(long id) {
        noteRepository.deleteById(id);
    }

    // Update an existing note
    public NotesModel updateFor(NotesModel noteModel){
        String username = getAuthenticatedUsername();
        NotesModel existingNote = noteRepository.findById(noteModel.getId())
                .orElseThrow(() -> new NoSuchElementException("Note not found with ID: " + noteModel.getId()));

        if (!existingNote.getAuthor().getUsername().equals(username)) {
            throw new UnauthorizedAccessException("No tienes permiso para editar esta nota");
        }

        existingNote.setTitle(noteModel.getTitle());
        existingNote.setDescription(noteModel.getDescription());
        existingNote.setComplete(noteModel.isComplete());

        return noteRepository.save(existingNote);
    }

    public List<NotesModel> findByTitleContainignFor(String title) {
        String username = getAuthenticatedUsername();
        AppUser user = appUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        return noteRepository.findByTitleContainingIgnoreCaseAndAuthor(title, user);
    }

    private String getAuthenticatedUsername() {
        String username = SecurityUtils.getCurrentUsername();
        if (username == null)
            throw new UnauthorizedAccessException("No hay usuario autenticado");
        return username;
    }
}