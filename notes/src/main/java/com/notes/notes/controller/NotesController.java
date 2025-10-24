package com.notes.notes.controller;

import com.notes.notes.exception.NoteNotFoundException;
import com.notes.notes.model.NotesModel;
import com.notes.notes.service.NotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/notes")
public class NotesController {

    private final NotesService noteService;

    @Autowired
    public NotesController(NotesService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    public String listNotes(Model model) {
        List<NotesModel> notes = noteService.getAllNotes();
        model.addAttribute("notes", notes);
        return "index";
    }

    @GetMapping("/{id}")
    public String seeDetailsNote(@PathVariable long id, Model model) {
        NotesModel noteModel = noteService.getNoteById(id)
                .orElseThrow(() -> new NoteNotFoundException(id));
        model.addAttribute("note", noteModel);
        return "noteDetails";
    }

    @PostMapping("/{id}/update")
    public String updateNote(@PathVariable long id, @ModelAttribute NotesModel updatedNote) {
        updatedNote.setId(id);
        noteService.updateNote(updatedNote);
        return "redirect:/notes";
    }

    @GetMapping("/newNote")
    public String showNewNoteForm(Model model) {
        model.addAttribute("note", new NotesModel());
        return "newNote";
    }

    @PostMapping("/newNote")
    public String newNote(@ModelAttribute NotesModel noteModel) {
        noteService.createNewNote(noteModel);
        return "redirect:/notes";
    }


    @PostMapping("/{id}/delete")
    public String deleteNote(@PathVariable long id) {
        noteService.deleteNote(id);
        return "redirect:/notes";
    }
}
