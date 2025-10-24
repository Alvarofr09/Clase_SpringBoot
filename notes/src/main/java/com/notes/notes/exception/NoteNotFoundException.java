package com.notes.notes.exception;

public class NoteNotFoundException extends RuntimeException {
    public NoteNotFoundException(Long id) {

        super("No se pudo encontrar la nota con ID: " + id);
    }
}