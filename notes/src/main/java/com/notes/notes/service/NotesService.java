package com.notes.notes.service;


import com.notes.notes.model.NotesModel;
import com.notes.notes.repository.NotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class NotesService {
    private final NotesRepository noteRepository;

    @Autowired
    public NotesService(NotesRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    // Get all notes
    public List<NotesModel> getAllNotes() {
        return noteRepository.findAll();
    }

    // Get note by id
    public Optional<NotesModel> getNoteById(long id) {
        return noteRepository.findById(id);
    }

    // Create a new note
    public NotesModel createNewNote(NotesModel noteModel) {
        return noteRepository.save(noteModel);
    }

    // Delete a note
    public void deleteNote(long id) {
        noteRepository.deleteById(id);
    }

    // Update an existing note
    public NotesModel updateNote(NotesModel noteModel) {
        NotesModel existingNote = noteRepository.findById(noteModel.getId())
                .orElseThrow(() -> new NoSuchElementException("Note not found with ID: " + noteModel.getId()));

        existingNote.setTitle(noteModel.getTitle());
        existingNote.setDescription(noteModel.getDescription());
        existingNote.setComplete(noteModel.isComplete());

        return noteRepository.save(existingNote);
    }


}