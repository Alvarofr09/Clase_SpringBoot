package com.notes.notes.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Maneja la excepción cuando una tarea no se encuentra
    @ExceptionHandler(NoteNotFoundException.class)
    public ModelAndView handleNoteNotFound(NoteNotFoundException ex) {
        // 1. Crear un ModelAndView. Usaremos una nueva platinlla: 'error-404'
        ModelAndView modelAndView = new ModelAndView("error-404");

        // 2. Añadir el mensaje de error al modelo.
        modelAndView.addObject("errorMessage", ex.getMessage());
        modelAndView.addObject("status", HttpStatus.NOT_FOUND.value()); // Código 404

        // 3. Establecer el codigo de estado HTTP 404.
        modelAndView.setStatus(HttpStatus.NOT_FOUND);

        return modelAndView;
    }
}