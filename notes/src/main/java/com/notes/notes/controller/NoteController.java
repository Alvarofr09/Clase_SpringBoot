package com.notes.notes.controller;

import com.notes.notes.exception.NoteNotFoundException;
import com.notes.notes.model.NoteModel;
import com.notes.notes.service.NoteService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @ModelAttribute("newNote")
    public NoteModel newNote() {
        return new NoteModel();
    }

    @GetMapping
    public String listNotes(Model model,
                            @AuthenticationPrincipal User authUser) {
        model.addAttribute("notes", noteService.findAllFor(authUser.getUsername()));
        return "index";
    }

    @GetMapping("/notes")
    public String listNotesAlias(Model model,
                                 @AuthenticationPrincipal User authUser) {
        model.addAttribute("notes", noteService.findAllFor(authUser.getUsername()));
        return "index";
    }

    @GetMapping("/notes/{id}")
    public String viewNote(@PathVariable Long id,
                           Model model,
                           @AuthenticationPrincipal User authUser) {
        var note = noteService.findByIdFor(id, authUser.getUsername())
                .orElseThrow(() -> new NoteNotFoundException(id));
        model.addAttribute("note", note);
        return "noteDetails";
    }

    @GetMapping("/notes/create")
    public String showCreateForm() {
        return "newNote";
    }

    @PostMapping("/notes/new")
    public String createNote(@ModelAttribute("newNote") NoteModel note,
                             @AuthenticationPrincipal User authUser) {
        noteService.createFor(note, authUser.getUsername());
        return "redirect:/";
    }

    @PutMapping("/notes/{id}")
    public String updateNote(@PathVariable Long id,
                             @ModelAttribute("note") NoteModel updatedNote,
                             @AuthenticationPrincipal User authUser) {
        noteService.updateFor(id, updatedNote, authUser.getUsername());
        return "redirect:/notes/" + id;
    }

    @DeleteMapping("/notes/{id}")
    public String deleteNote(@PathVariable Long id,
                             @AuthenticationPrincipal User authUser) {
        noteService.deleteByIdFor(id, authUser.getUsername());
        return "redirect:/";
    }
}
