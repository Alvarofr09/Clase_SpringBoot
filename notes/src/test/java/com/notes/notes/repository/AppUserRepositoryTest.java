package com.notes.notes.repository;

import com.notes.notes.model.AppUserModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class AppUserRepositoryTest {
    @Autowired
    private AppUserRepository repository;

    @Test
    void testFindByUsername_Exist() {
        AppUserModel user = new AppUserModel("usuario", "pass", "ROLE_USER");
        repository.save(user);

        Optional<AppUserModel> found = repository.findByUsername("usuario");

        System.out.println(found);

        assertTrue(found.isPresent());
        assertEquals("usuario", found.get().getUsername());
    }

    @Test
    void testFindByUsername_NotExist() {
        boolean exists = repository.existsByUsername("usuario");
        assertFalse(exists);
    }
}
