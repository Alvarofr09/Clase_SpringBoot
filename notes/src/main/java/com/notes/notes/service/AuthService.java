package com.notes.notes.service;

import com.notes.notes.exception.DuplicateUsernameException;
import com.notes.notes.model.AppUserModel;
import com.notes.notes.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthService {
    private final AppUserRepository repository;
    private final PasswordEncoder encoder;

    @Autowired
    public AuthService(AppUserRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    public void register(String username, String rawPassword) {
        // validacion si existe el usuario
        if (repository.existsByUsername(username)) {
            throw new DuplicateUsernameException("El usuario ya existe");
        }
        AppUserModel u = new AppUserModel();
        u.setUsername(username);
        u.setPassword(encoder.encode(rawPassword));
        u.setRole("ROLE_USER");

        repository.save(u);
    }
}
