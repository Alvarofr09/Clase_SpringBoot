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
        // 1. Crear un ModelAndView. Usaremos la plantilla: 'error'
        ModelAndView modelAndView = new ModelAndView("error");

        // 2. Añadir el mensaje de error al modelo.
        modelAndView.addObject("errorMessage", ex.getMessage());
        modelAndView.addObject("status", HttpStatus.NOT_FOUND.value()); // Código 404

        // 3. Establecer el codigo de estado HTTP 404.
        modelAndView.setStatus(HttpStatus.NOT_FOUND);

        return modelAndView;
    }

    // Maneja la excepción cuando un usuario intenta editar una nota de otra persona
    @ExceptionHandler(UnauthorizedAccessException.class)
    public ModelAndView UnathorizedAccess(UnauthorizedAccessException ex) {
        // 1. Crear un ModelAndView. Usaremos la plantilla: 'error'
        ModelAndView modelAndView = new ModelAndView("error");

        // 2. Añadir el mensaje de error al modelo.
        modelAndView.addObject("errorMessage", ex.getMessage());
        modelAndView.addObject("status", HttpStatus.METHOD_NOT_ALLOWED.value()); // Código 405

        // 3. Establecer el codigo de estado HTTP 405.
        modelAndView.setStatus(HttpStatus.METHOD_NOT_ALLOWED);

        return modelAndView;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleGeneral(Exception ex) {
        // 1. Crear un ModelAndView. Usaremos la plantilla: 'error'
        ModelAndView modelAndView = new ModelAndView("error");

        // 2. Añadir el mensaje de error al modelo.
        modelAndView.addObject("errorMessage", "Error interno del servidor");
        modelAndView.addObject("status", HttpStatus.INTERNAL_SERVER_ERROR.value()); // Código 500

        // 3. Establecer el codigo de estado HTTP 500.
        modelAndView.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);

        return modelAndView;
    }
}