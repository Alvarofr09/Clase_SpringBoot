package com.notes.notes.controller;

import com.notes.notes.dto.RegisterDto;
import com.notes.notes.exception.DuplicateUsernameException;
import com.notes.notes.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Validated
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    // Muestra formulario de registro
    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("userDto", new RegisterDto("",""));
        return "register";
    }

    // Procesar el registro
    @PostMapping("/register")
    public String Register(@ModelAttribute("userDto") @Validated RegisterDto dto, Model model) {
        try {
            authService.register(dto.username(), dto.password());
            return "redirect:/login?registered";
        } catch (DuplicateUsernameException e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }

    @GetMapping("/logout")
    public String logout(){
        return "login";
    }
}
