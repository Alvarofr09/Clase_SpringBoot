package com.notes.notes.controller;

import com.notes.notes.dto.RegisterDto;
import com.notes.notes.model.AppUser;
import com.notes.notes.repository.AppUserRepository;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Validated
public class AuthController {

    private final AppUserRepository repository;
    private final PasswordEncoder encoder;

    @Autowired
    public AuthController(AppUserRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
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
        // validacion si existe el usuario
        if (repository.existsByUsername(dto.username())) {
            model.addAttribute("error", "El usuario ya existe");
            return "register";
        }
        AppUser u = new AppUser();
        u.setUsername(dto.username());
        u.setPassword(encoder.encode(dto.password()));
        u.setRole("ROLE_USER");

        repository.save(u);

        return "redirect:/login?registered";
    }

    @GetMapping("/logout")
    public String logout(){
        return "login";
    }
}
