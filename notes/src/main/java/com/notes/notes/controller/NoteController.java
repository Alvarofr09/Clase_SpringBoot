package com.notes.notes.controller;

import com.notes.notes.exception.NoteNotFoundException;
import com.notes.notes.model.NoteModel;
import com.notes.notes.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/notes")
public class NoteController {

    private final NoteService noteService;

    @Autowired
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @ModelAttribute("newNote")
    public NoteModel newNote() { return new NoteModel();}

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
    public String createNote(@ModelAttribute("newNote") NoteModel noteModel) {
        noteService.createFor(noteModel);
        return "redirect:/notes";
    }

    @GetMapping("/{id:[0-9]+}")
    public String seeDetailsNote(@PathVariable long id, Model model) {
        NoteModel noteModel = noteService.findByIdFor(id)
                .orElseThrow(() -> new NoteNotFoundException(id));
        model.addAttribute("note", noteModel);
        return "noteDetails";
    }

    @PutMapping("/{id}")
    public String updateNote(@ModelAttribute("note") NoteModel updatedNote) {
        noteService.updateFor(updatedNote);
        return "redirect:/notes";
    }

    @DeleteMapping("/{id}")
    public String deleteNote(@PathVariable long id) {
        noteService.deleteNote(id);
        return "redirect:/notes";
    }
}
