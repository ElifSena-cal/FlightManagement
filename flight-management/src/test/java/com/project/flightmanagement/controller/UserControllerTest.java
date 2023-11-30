package com.project.flightmanagement.controller;

import com.project.flightmanagement.request.UserRequest;
import com.project.flightmanagement.response.UserResponse;
import com.project.flightmanagement.service.UserService;
import com.project.flightmanagement.util.JsonUtils;
import com.project.flightmanagement.util.MockMvcUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcUtils.buildMockMvc(userController);
    }

    @Test
    void listUsers_shouldReturnListOfUserResponses() throws Exception {
        List<UserResponse> userList = Arrays.asList(new UserResponse(), new UserResponse());
        when(userService.getAllUsers()).thenReturn(userList);

        mockMvc.perform(MockMvcRequestBuilders.get("/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2));

        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void createUser_shouldReturnCreatedUserRequest() throws Exception {
        UserRequest request = new UserRequest();
        UserRequest response = new UserRequest();
        when(userService.createUser(any())).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.post("/users/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.toJson(request)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        verify(userService, times(1)).createUser(any());
    }

    @Test
    void getUserById_existingUser_shouldReturnUserResponse() throws Exception {
        Long userId = 1L;
        UserResponse response = new UserResponse();
        when(userService.getUserById(userId)).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.get("/users/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(userService, times(1)).getUserById(userId);
    }

    @Test
    void updateUser_shouldReturnUpdatedUserRequest() throws Exception {
        UserRequest request = new UserRequest();
        UserRequest response = new UserRequest();
        when(userService.updateUser(any())).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.put("/users/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.toJson(request)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(userService, times(1)).updateUser(any());
    }

    @Test
    void deleteUser_existingUser_shouldReturnNoContent() throws Exception {
        Long userId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/users/delete/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(userService, times(1)).deleteUserById(userId);
    }
}
