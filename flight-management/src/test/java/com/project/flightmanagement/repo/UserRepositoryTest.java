package com.project.flightmanagement.repo;

import com.project.flightmanagement.entity.User;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @Order(1)
    @Rollback(value = false)
    public void shouldSaveUser() {
        User user = User.builder()
                .userName("testuser")
                .password("testpassword")
                .email("testuser@example.com")
                .build();
        userRepository.save(user);
        assertNotNull(user.getId());
        assertTrue(user.getId() > 0);
    }

    @Test
    @Order(2)
    public void shouldReturnUserWhenValidIdIsGiven() {
        User user = userRepository.findById(1L).orElse(null);
        assertNotNull(user);
        assertEquals(1L, user.getId());
    }

    @Test
    @Order(3)
    public void shouldReturnAllUsers() {
        List<User> userList = userRepository.findAll();
        assertNotNull(userList);
        assertEquals(1, userList.size());
    }
    @Test
    @Order(4)
    public void shouldFindByUserName() {
        Optional<User> userOptional = userRepository.findByUserName("testuser");
        assertTrue(userOptional.isPresent());
        assertEquals("testuser", userOptional.get().getUserName());
    }
    @Test
    @Order(5)
    @Rollback(value = false)
    public void shouldUpdateUser() {
        User user = userRepository.findById(1L).orElse(null);
        assertNotNull(user);
        user.setUserName("updatedUsername");
        user.setPassword("updatedPassword");
        user.setEmail("updateduser@example.com");
        User updatedUser = userRepository.save(user);
        assertEquals("updatedUsername", updatedUser.getUserName());
        assertEquals("updatedPassword", updatedUser.getPassword());
        assertEquals("updateduser@example.com", updatedUser.getEmail());
    }

    @Test
    @Order(6)
    @Rollback(value = false)
    public void shouldDeleteUser() {
        User user = userRepository.findById(1L).orElse(null);
        assertNotNull(user);
        userRepository.deleteById(user.getId());
        assertTrue(userRepository.findById(user.getId()).isEmpty());
    }
    @Test
    @Order(7)
    public void shouldNotFindByNonexistentUserName() {
        Optional<User> userOptional = userRepository.findByUserName("nonexistentuser");
        assertTrue(userOptional.isEmpty());
    }

}
