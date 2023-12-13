package com.project.flightmanagement.controller;
import com.project.flightmanagement.exception.EntityNotFoundException;
import com.project.flightmanagement.request.UserRequest;
import com.project.flightmanagement.response.UserResponse;
import com.project.flightmanagement.service.KeyCloakService;
import com.project.flightmanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;


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

    @PostMapping(value = "/saveUsers", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = "application/json")
    public CompletableFuture<List<UserResponse>> saveUsers(@RequestParam(value = "files") MultipartFile[] files) throws Exception {
        return userService.saveUser(files);
    }

    @GetMapping(value = "/users", produces = "application/json")
    public CompletableFuture<List<UserResponse>> findAllUsers() {
        return  userService.findAllUsers();
    }

    @GetMapping(value = "/getUsersByThread", produces = "application/json")
    public ResponseEntity<List<UserResponse>> getUsers() {
        CompletableFuture<List<UserResponse>> users1 = userService.findAllUsers();
        CompletableFuture<List<UserResponse>> users2 = userService.findAllUsers();
        CompletableFuture<List<UserResponse>> users3 = userService.findAllUsers();

        CompletableFuture<Void> allOf = CompletableFuture.allOf(users1, users2, users3);

        List<UserResponse> combinedResult = allOf.thenApply(
                v -> Stream.of(users1, users2, users3)
                        .map(CompletableFuture::join)
                        .flatMap(List::stream)
                        .collect(Collectors.toList())
        ).join();

        return ResponseEntity.status(HttpStatus.OK).body(combinedResult);
    }
}

