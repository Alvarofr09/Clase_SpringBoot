package com.notes.notes.controller;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class NotesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testListNotes() throws Exception {
        // Test de GET /notes
    }

    @Test
    void testSeeNoteDetails() throws Exception {
        // Test de GET /notes/{id}
    }

    @Test
    void testCreateNote() throws Exception {
        // Test de POST /notes/newNote
    }

    @Test
    void testUpdateNote() throws Exception {
        // Test de POST /notes/{id}/update
    }

    @Test
    void testDeleteNote() throws Exception {
        // Test de POST /notes/{id}/delete
    }
}

