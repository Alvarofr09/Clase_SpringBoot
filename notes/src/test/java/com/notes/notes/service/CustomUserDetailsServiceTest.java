package com.notes.notes.service;

import com.notes.notes.model.AppUser;
import com.notes.notes.repository.AppUserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class CustomUserDetailsServiceTest {

    @Mock
    private AppUserRepository repository;

    @InjectMocks
    private CustomUserDetailsService service;

    @Test
    void loadUserByUsername_UserExist() {
        AppUser user = new AppUser(1L, "usuario", "pass", "ROLE_USER");
        when(repository.findByUsername("usuario")).thenReturn(Optional.of(user));

        UserDetails result = service.loadUserByUsername("usuario");

        assertEquals("usuario", result.getUsername());
        assertEquals("pass", result.getPassword());
        assertTrue(result.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_USER")));
    }

    @Test
    void loadUserByUsername_UserNotExist() {
        when(repository.findByUsername("usuario")).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class,
                () -> service.loadUserByUsername("usuario"));
    }
}
