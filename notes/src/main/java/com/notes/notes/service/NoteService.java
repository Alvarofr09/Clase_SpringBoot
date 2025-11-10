package com.notes.notes.service;

import com.notes.notes.exception.UnauthorizedAccessException;
import com.notes.notes.model.AppUserModel;
import com.notes.notes.model.NoteModel;
import com.notes.notes.repository.AppUserRepository;
import com.notes.notes.repository.NoteRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class NoteService {

    private final NoteRepository noteRepository;
    private final AppUserRepository appUserRepository;

    public NoteService(NoteRepository noteRepository, AppUserRepository appUserRepository) {
        this.noteRepository = noteRepository;
        this.appUserRepository = appUserRepository;
    }

    // Obtener todas las notas de un usuario
    public List<NoteModel> findAllFor(String username) {
        return noteRepository.findByAuthorUsername(username);
    }

    // Buscar una nota por ID solo si pertenece al usuario
    public Optional<NoteModel> findByIdFor(Long id, String username) {
        return noteRepository.findByIdAndAuthorUsername(id, username);
    }

    // Crear una nueva nota
    public NoteModel createFor(NoteModel noteModel, String username) {
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

    // Actualizar una nota
    public NoteModel updateFor(Long id, NoteModel updatedNote, String username) {
        NoteModel existingNote = noteRepository.findByIdAndAuthorUsername(id, username)
                .orElseThrow(() -> new NoSuchElementException("Nota no encontrada o no pertenece al usuario"));

        existingNote.setTitle(updatedNote.getTitle());
        existingNote.setDescription(updatedNote.getDescription());
        existingNote.setComplete(updatedNote.isComplete());

        return noteRepository.save(existingNote);
    }

    // Eliminar una nota
    public void deleteByIdFor(Long id, String username) {
        NoteModel note = noteRepository.findByIdAndAuthorUsername(id, username)
                .orElseThrow(() -> new UnauthorizedAccessException("No tienes permiso para eliminar esta nota"));
        noteRepository.delete(note);
    }

    // Buscar notas por título
    public List<NoteModel> findByTitleContainingFor(String title, String username) {
        AppUserModel user = appUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        return noteRepository.findByTitleContainingIgnoreCaseAndAuthor(title, user);
    }
}
