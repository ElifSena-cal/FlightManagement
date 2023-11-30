package com.project.flightmanagement.controller;
import com.project.flightmanagement.exception.EntityNotFoundException;
import com.project.flightmanagement.request.UserRequest;
import com.project.flightmanagement.response.UserResponse;
import com.project.flightmanagement.service.KeyCloakService;
import com.project.flightmanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    @GetMapping
    public List<UserResponse> listUsers(){
        return userService.getAllUsers();
    }

    @PostMapping("/create")
    public UserRequest createUser(@RequestBody UserRequest newUser)  {
        return userService.createUser(newUser);
    }

    @GetMapping("/{userId}")
    public UserResponse getUserById(@PathVariable Long userId) {
        return userService.getUserById(userId);
    }

    @PutMapping("/update")
    public UserRequest updateUser(@RequestBody UserRequest updatedUser){
        return  userService.updateUser(updatedUser);
    }

    @DeleteMapping("/delete/{userId}")
    public void deleteUser(@PathVariable Long userId){
          userService.deleteUserById(userId);
    }
}

