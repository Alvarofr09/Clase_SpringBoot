package com.notes.notes.service;

import com.notes.notes.model.NotesModel;
import com.notes.notes.repository.NotesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class NotesServiceTest {

    @Mock
    private NotesRepository notesRepository;

    @InjectMocks
    private NotesService notesService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllNotes() {
        // Arrange
        // Crear algunas notas
        NotesModel nota1 = new NotesModel();
        nota1.setTitle("Nota 1");
        nota1.setDescription("Descripcion 1");
        nota1.setComplete(true);

        NotesModel nota2 = new NotesModel();
        nota2.setTitle("Nota 2");
        nota2.setDescription("Descripcion 2");
        nota2.setComplete(false);
        when(notesRepository.findAll()).thenReturn(Arrays.asList(nota1, nota2));

        // ACT
        List<NotesModel> result = notesService.getAllNotes();

        // ASSERT
        assertEquals("Nota 1", result.get(0).getTitle());
        assertEquals("Descripcion 2", result.get(1).getDescription());
    }

    @Test
    void testGetNoteById_Found() {
        // Test de obtener nota por id
        // ARRANGE
        NotesModel nota1 = new NotesModel();
        nota1.setId(1L);
        when(notesRepository.findById(1L)).thenReturn(Optional.of(nota1));

        Optional<NotesModel> result = notesService.getNoteById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    void testGetNoteById_NotFound() {
        // Test de obtener nota por id
        // ARRANGE
        when(notesRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<NotesModel> result = notesService.getNoteById(99L);

        assertTrue(result.isEmpty());
    }

    @Test
    void testCreateNewNote() {
        // Test de crear nueva nota
        // Arrange
        NotesModel nota = new NotesModel();
        nota.setTitle("Prueba");

        when(notesRepository.save(nota)).thenReturn(nota);

        NotesModel result = notesService.createNewNote(nota);

        assertEquals("Prueba", result.getTitle());
        verify(notesRepository, times(1)).save(nota);
    }

    @Test
    void testUpdateNote() {
        // Test de actualizar nota
    }

    @Test
    void testDeleteNote() {
        // Test de borrar nota
    }
}
