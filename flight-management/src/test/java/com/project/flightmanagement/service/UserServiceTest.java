package com.project.flightmanagement.service;

import com.project.flightmanagement.entity.User;
import com.project.flightmanagement.enums.Role;
import com.project.flightmanagement.exception.EntityNotFoundException;
import com.project.flightmanagement.mapper.UserMapper;
import com.project.flightmanagement.repo.UserRepository;
import com.project.flightmanagement.request.UserRequest;
import com.project.flightmanagement.response.UserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @Mock
    private KeyCloakService keyCloakService;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAllUsers_shouldReturnListOfUserResponses() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(new User(), new User()));
        when(userMapper.usersToUserResponses(anyList()))
                .thenReturn(Arrays.asList(new UserResponse(), new UserResponse()));

        List<UserResponse> result = userService.getAllUsers();

        assertEquals(2, result.size());
    }

    @Test
    void createUser_shouldReturnUserRequest() {
        UserRequest userRequest = new UserRequest();
        User user = new User();
        userRequest.setPassword("test");
        when(userMapper.userRequestToUser(userRequest)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.userToUserRequest(user)).thenReturn(userRequest);

        UserRequest result = userService.createUser(userRequest);

        assertEquals(userRequest, result);

    }

    @Test
    void getUserById_existingUser_shouldReturnUserResponse() {
        Long userId = 1L;
        User user = new User();
        UserResponse expectedResponse = new UserResponse();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userMapper.userToUserResponse(user)).thenReturn(expectedResponse);

        UserResponse result = userService.getUserById(userId);

        assertEquals(expectedResponse, result);
    }

    @Test
    void getUserById_nonExistingUser_shouldThrowEntityNotFoundException() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.getUserById(userId));
    }

    @Test
    void updateUser_existingUser_shouldReturnUpdatedUserRequest() {
        UserRequest updatedUserRequest = new UserRequest();
        updatedUserRequest.setId(1L);

        User existingUser = new User();
        when(userRepository.findById(updatedUserRequest.getId())).thenReturn(Optional.of(existingUser));
        when(userRepository.save(existingUser)).thenReturn(existingUser);
        when(userMapper.userToUserRequest(existingUser)).thenReturn(updatedUserRequest);

        UserRequest result = userService.updateUser(updatedUserRequest);

        assertEquals(updatedUserRequest, result);
    }

    @Test
    void updateUser_nonExistingUser_shouldThrowEntityNotFoundException() {
        UserRequest updatedUserRequest = new UserRequest();
        updatedUserRequest.setId(1L);
        when(userRepository.findById(updatedUserRequest.getId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.updateUser(updatedUserRequest));
    }

    @Test
    void deleteUserById_existingUser_shouldDeleteUser() {
        Long userId = 1L;
        User existingUser = new User();
        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));

        userService.deleteUserById(userId);

        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void deleteUserById_nonExistingUser_shouldThrowEntityNotFoundException() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.deleteUserById(userId));
    }

    @Test
    void getOneUserByUserName_existingUser_shouldReturnUser() {
        String userName = "testUser";
        User user = new User();
        when(userRepository.findByUserName(userName)).thenReturn(Optional.of(user));

        User result = userService.getOneUserByUserName(userName);

        assertEquals(user, result);
    }

    @Test
    void getOneUserByUserName_nonExistingUser_shouldThrowUsernameNotFoundException() {
        String userName = "nonExistingUser";
        when(userRepository.findByUserName(userName)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userService.getOneUserByUserName(userName));
    }

    @Test
    void loadUserByUsername_existingUser_shouldReturnUserDetails() {
        String userName = "testUser";
        User user = new User();
        user.setUserName(userName);
        user.setPassword("hashedPassword");
        user.setRole(Role.USER);

        when(userRepository.findByUserName(userName)).thenReturn(Optional.of(user));

        UserDetails userDetails = userService.loadUserByUsername(userName);

        assertNotNull(userDetails);
        assertEquals(userName, userDetails.getUsername());
        assertTrue(userDetails.getAuthorities().contains(new SimpleGrantedAuthority(Role.USER.name())));
    }

    @Test
    void loadUserByUsername_nonExistingUser_shouldThrowUsernameNotFoundException() {
        String userName = "nonExistingUser";
        when(userRepository.findByUserName(userName)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername(userName));
    }
}
