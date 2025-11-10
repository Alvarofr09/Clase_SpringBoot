package com.notes.notes.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class NotesRepositoryTest {

    @Autowired
    private NoteRepository notesRepository;

    @Test
    void testSaveNote() {
        // Test de guardar nota
    }

    @Test
    void testFindById() {
        // Test de buscar nota por id
    }

    @Test
    void testFindAll() {
        // Test de buscar todas las notas
    }

    @Test
    void testDeleteNote() {
        // Test de borrar nota
    }
}
