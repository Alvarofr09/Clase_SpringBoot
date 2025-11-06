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

import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NotesServiceTest {

    @Mock
    private NotesRepository notesRepository;

    @Mock
    private AppUserRepository appUserRepository;

    @InjectMocks
    private NotesService notesService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllNotesByUsername() {
        Principal mockPrincipal = () -> "usuario";
        NotesModel nota1 = new NotesModel("Nota 1", "desc", false, new AppUser());
        NotesModel nota2 = new NotesModel("Nota 2", "desc", true, new AppUser());

        when(notesRepository.findByAuthorUsername("usuario")).thenReturn(Arrays.asList(nota1, nota2));

        List<NotesModel> result = notesService.getAllNotesByUsername(mockPrincipal);

        assertEquals(2, result.size());
        assertEquals("Nota 1", result.get(0).getTitle());
        verify(notesRepository).findByAuthorUsername("usuario");
    }

    @Test
    void testGetNoteById_Found() {
        NotesModel nota1 = new NotesModel("T", "D", false, new AppUser());
        nota1.setId(1L);

        when(notesRepository.findById(1L)).thenReturn(Optional.of(nota1));

        Optional<NotesModel> result = notesService.getNoteById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        verify(notesRepository).findById(1L);
    }

    @Test
    void testGetNoteById_NotFound() {
        when(notesRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<NotesModel> result = notesService.getNoteById(99L);

        assertTrue(result.isEmpty());
        verify(notesRepository).findById(99L);
    }

    @Test
    void testCreateNewNote_AssignsAuthorCorrectly() {
        Principal mockPrincipal = () -> "usuario";
        AppUser user = new AppUser("usuario", "pass", "ROLE_USER");

        when(appUserRepository.findByUsername("usuario")).thenReturn(Optional.of(user));
        when(notesRepository.save(any(NotesModel.class))).thenAnswer(invocation -> invocation.getArgument(0));

        NotesModel nota = new NotesModel("Título", "Descripción", false, null);

        NotesModel result = notesService.createNewNote(nota, mockPrincipal);

        assertEquals("Título", result.getTitle());
        assertEquals("usuario", result.getAuthor().getUsername());
        verify(notesRepository, times(1)).save(any(NotesModel.class));
    }

    @Test
    void testCreateNewNote_UserNotFound() {
        Principal mockPrincipal = () -> "usuario";
        when(appUserRepository.findByUsername("usuario")).thenReturn(Optional.empty());

        NotesModel nota = new NotesModel("T", "D", false, null);

        assertThrows(RuntimeException.class, () -> notesService.createNewNote(nota, mockPrincipal));
    }

    @Test
    void testDeleteNote() {
        doNothing().when(notesRepository).deleteById(1L);

        notesService.deleteNoteById(1L);

        verify(notesRepository).deleteById(1L);
    }
}
