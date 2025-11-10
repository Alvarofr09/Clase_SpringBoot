package com.notes.notes.service;


import com.notes.notes.exception.UnauthorizedAccessException;
import com.notes.notes.model.AppUserModel;
import com.notes.notes.model.NoteModel;
import com.notes.notes.repository.AppUserRepository;
import com.notes.notes.repository.NoteRepository;
import com.notes.notes.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class NoteService {
    private final NoteRepository noteRepository;
    private final AppUserRepository appUserRepository;

    @Autowired
    public NoteService(NoteRepository noteRepository, AppUserRepository appUserRepository) {
        this.noteRepository = noteRepository;
        this.appUserRepository = appUserRepository;
    }

    // Get all notes
    public List<NoteModel> findAllForCurrentUser() {
        String username = getAuthenticatedUsername();
        return noteRepository.findByAuthorUsername(username);
    }

    // Get note by id
    public Optional<NoteModel> findByIdFor(long id) {
        String username = getAuthenticatedUsername();
        return noteRepository.findByIdAndAuthorUsername(id, username);
    }

    // Create a new note
    public NoteModel createFor(NoteModel noteModel) {
        // Buscar el usuario
        String username = getAuthenticatedUsername();
        AppUserModel user = appUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        NoteModel newNote = new NoteModel(
                noteModel.getTitle(),
                noteModel.getDescription(),
                noteModel.isComplete(),
                user
        );

        return noteRepository.save(newNote);
    }

    // Delete a note
    public void deleteNote(long id) {
        noteRepository.deleteById(id);
    }

    // Update an existing note
    public NoteModel updateFor(NoteModel noteModel){
        String username = getAuthenticatedUsername();
        NoteModel existingNote = noteRepository.findById(noteModel.getId())
                .orElseThrow(() -> new NoSuchElementException("Note not found with ID: " + noteModel.getId()));

        if (!existingNote.getAuthor().getUsername().equals(username)) {
            throw new UnauthorizedAccessException("No tienes permiso para editar esta nota");
        }

        existingNote.setTitle(noteModel.getTitle());
        existingNote.setDescription(noteModel.getDescription());
        existingNote.setComplete(noteModel.isComplete());

        return noteRepository.save(existingNote);
    }

    public List<NoteModel> findByTitleContainignFor(String title) {
        String username = getAuthenticatedUsername();
        AppUserModel user = appUserRepository.findByUsername(username)
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