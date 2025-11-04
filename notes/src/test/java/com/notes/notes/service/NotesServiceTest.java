package com.notes.notes.service;

import com.notes.notes.model.AppUser;
import com.notes.notes.model.NotesModel;
import com.notes.notes.repository.AppUserRepository;
import com.notes.notes.repository.NotesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class NotesServiceTest {

    @Mock
    private NotesRepository notesRepository;

    @Mock
    private AppUserRepository userRepository;

    @InjectMocks
    private NotesService notesService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllNotesByUsername() {
        Principal mockPrincipal = () -> "usuario";
        NotesModel nota1 = new NotesModel();
        nota1.setTitle("Nota 1");
        NotesModel nota2 = new NotesModel();
        nota2.setTitle("Nota 2");

        when(notesRepository.findByAuthorUsername("usuario")).thenReturn(Arrays.asList(nota1, nota2));

        List<NotesModel> result = notesService.getAllNotesByUsername(mockPrincipal);

        assertEquals(2, result.size());
        assertEquals("Nota 1", result.get(0).getTitle());
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
        // Arrange
        Principal mockPrincipal = () -> "usuario";
        AppUser user = new AppUser();
        user.setUsername("usuario");

        when(userRepository.findByUsername("usuario")).thenReturn(Optional.of(user));

        NotesModel nota = new NotesModel();
        nota.setTitle("Prueba");
        nota.setDescription("Desc");
        nota.setComplete(false);

        when(notesRepository.save(any(NotesModel.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        NotesModel result = notesService.createNewNote(nota, mockPrincipal);

        // Assert
        assertEquals("Prueba", result.getTitle());
        assertEquals("usuario", result.getAuthor().getUsername());
        verify(notesRepository, times(1)).save(any(NotesModel.class));
    }


    @Test
    void testUpdateNote() {
        // Test de actualizar nota
        // Arrange
        Principal mockPrincipal = () -> "usuario";
        AppUser user = new AppUser();
        user.setUsername("usuario");

        when(userRepository.findByUsername("usuario")).thenReturn(Optional.of(user));

        NotesModel nota = new NotesModel();
        nota.setTitle("Prueba");
        nota.setDescription("Desc");
        nota.setComplete(false);

        when(notesRepository.save(any(NotesModel.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        NotesModel result = notesService.createNewNote(nota, mockPrincipal);
    }

    @Test
    void testDeleteNote() {
        // Test de borrar nota
        NotesModel nota1 = new NotesModel();
        nota1.setId(1L);
        when(notesRepository.findById(1L)).thenReturn(Optional.of(nota1));

        Optional<NotesModel> result = notesService.getNoteById(1L);
    }
}
