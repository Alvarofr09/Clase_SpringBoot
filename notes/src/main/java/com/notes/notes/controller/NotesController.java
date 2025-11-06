package com.notes.notes.controller;

import com.notes.notes.exception.NoteNotFoundException;
import com.notes.notes.model.NotesModel;
import com.notes.notes.service.NotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/notes")
public class NotesController {

    private final NotesService noteService;

    @Autowired
    public NotesController(NotesService noteService) {
        this.noteService = noteService;
    }

    @ModelAttribute("newNote")
    public NotesModel newNote() { return new NotesModel();}

    @GetMapping
    public String listNotes(@RequestParam(value = "search", required = false) String search,
                            Model model) {
        if (search != null && !search.isEmpty()) {
            model.addAttribute("notes", noteService.findByTitleContainignFor(search));
        } else {
            model.addAttribute("notes", noteService.findAllForCurrentUser());
        }
        return "index";
    }

    @GetMapping("/create")
    public String showCreateForm() {
        return "newNote";
    }

    @PostMapping("/create")
    public String createNote(@ModelAttribute("newNote") NotesModel noteModel) {
        noteService.createFor(noteModel);
        return "redirect:/notes";
    }

    @GetMapping("/{id:[0-9]+}")
    public String seeDetailsNote(@PathVariable long id, Model model) {
        NotesModel noteModel = noteService.findByIdFor(id)
                .orElseThrow(() -> new NoteNotFoundException(id));
        model.addAttribute("note", noteModel);
        return "noteDetails";
    }

    @PutMapping("/{id}")
    public String updateNote(@ModelAttribute("note") NotesModel updatedNote) {
        noteService.updateFor(updatedNote);
        return "redirect:/notes";
    }

    @DeleteMapping("/{id}")
    public String deleteNote(@PathVariable long id) {
        noteService.deleteNote(id);
        return "redirect:/notes";
    }
}
