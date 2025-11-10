package com.notes.notes.service;

import com.notes.notes.model.AppUserModel;
import com.notes.notes.model.NoteModel;
import com.notes.notes.repository.AppUserRepository;
import com.notes.notes.repository.NoteRepository;
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
    private NoteRepository notesRepository;

    @Mock
    private AppUserRepository appUserRepository;

    @InjectMocks
    private NoteService notesService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllNotesByUsername() {
        Principal mockPrincipal = () -> "usuario";
        NoteModel nota1 = new NoteModel("Nota 1", "desc", false, new AppUserModel());
        NoteModel nota2 = new NoteModel("Nota 2", "desc", true, new AppUserModel());

        when(notesRepository.findByAuthorUsername("usuario")).thenReturn(Arrays.asList(nota1, nota2));

        List<NoteModel> result = notesService.findAllForCurrentUser();

        assertEquals(2, result.size());
        assertEquals("Nota 1", result.get(0).getTitle());
        verify(notesRepository).findByAuthorUsername("usuario");
    }

    @Test
    void testGetNoteById_Found() {
        NoteModel nota1 = new NoteModel("T", "D", false, new AppUserModel());
        nota1.setId(1L);

        when(notesRepository.findById(1L)).thenReturn(Optional.of(nota1));

        Optional<NoteModel> result = notesService.findByIdFor(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        verify(notesRepository).findById(1L);
    }

    @Test
    void testGetNoteById_NotFound() {
        when(notesRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<NoteModel> result = notesService.findByIdFor(99L);

        assertTrue(result.isEmpty());
        verify(notesRepository).findById(99L);
    }

    @Test
    void testCreateNewNote_AssignsAuthorCorrectly() {
        Principal mockPrincipal = () -> "usuario";
        AppUserModel user = new AppUserModel("usuario", "pass", "ROLE_USER");

        when(appUserRepository.findByUsername("usuario")).thenReturn(Optional.of(user));
        when(notesRepository.save(any(NoteModel.class))).thenAnswer(invocation -> invocation.getArgument(0));

        NoteModel nota = new NoteModel("Título", "Descripción", false, null);

        NoteModel result = notesService.createFor(nota);

        assertEquals("Título", result.getTitle());
        assertEquals("usuario", result.getAuthor().getUsername());
        verify(notesRepository, times(1)).save(any(NoteModel.class));
    }

    @Test
    void testCreateNewNote_UserNotFound() {
        Principal mockPrincipal = () -> "usuario";
        when(appUserRepository.findByUsername("usuario")).thenReturn(Optional.empty());

        NoteModel nota = new NoteModel("T", "D", false, null);

        assertThrows(RuntimeException.class, () -> notesService.createFor(nota));
    }

    @Test
    void testDeleteNote() {
        doNothing().when(notesRepository).deleteById(1L);

        notesService.deleteNote(1L);

        verify(notesRepository).deleteById(1L);
    }
}
