package com.project.flightmanagement.service;

import com.project.flightmanagement.entity.User;
import com.project.flightmanagement.enums.Role;
import com.project.flightmanagement.exception.EntityNotFoundException;
import com.project.flightmanagement.mapper.UserMapper;
import com.project.flightmanagement.repo.UserRepository;
import com.project.flightmanagement.request.UserRequest;
import com.project.flightmanagement.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserMapper mapper;
    private final BCryptPasswordEncoder passwordEncoder =new BCryptPasswordEncoder();
    private final KafkaTemplate<String,String> kafkaTemplate;
    private final KeyCloakService keyCloakService;
    Logger logger = LoggerFactory.getLogger(UserService.class);
    private static final String TOPIC = "NewTopic";
    public List<UserResponse> getAllUsers() {
        return mapper.usersToUserResponses(userRepository.findAll());
    }

    public UserRequest createUser(UserRequest newUser) {
           // kafkaTemplate.send(TOPIC,newUser.getUserName());
            User user =  mapper.userRequestToUser(newUser);
            user.setPassword(passwordEncoder.encode(newUser.getPassword()));
            userRepository.save(user);
           //keyCloakService.addUser(newUser);
            return  mapper.userToUserRequest(user);
    }

    public UserResponse getUserById(Long userId)  {
        return mapper.userToUserResponse(userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(userId, "User")));
    }

    public UserRequest updateUser(UserRequest updatedUser) {
            User existingUser = userRepository.findById(updatedUser.getId()).orElseThrow(() -> new EntityNotFoundException(updatedUser.getId(), "User"));
            mapper.updateUserFromUserRequest(updatedUser, existingUser);
            existingUser.setUpdateTime(LocalDateTime.now());
            keyCloakService.getUpdatedUserRepresentation(updatedUser);
            return mapper.userToUserRequest(userRepository.save(existingUser));
    }

    public void deleteUserById(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(userId, "User"));

        userRepository.deleteById(userId);
    }

    public User getOneUserByUserName(String userName)  {
        return userRepository.findByUserName(userName)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
    @Override
    public UserDetails loadUserByUsername(String userName){
        User user = getOneUserByUserName(userName);
        List<GrantedAuthority> authorities = new ArrayList<>();
        Role[] userRoles = new Role[]{user.getRole()};
        for (Role role : userRoles) {
            authorities.add(new SimpleGrantedAuthority(role.name()));
        }
        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), authorities);
    }

    @Async
    public CompletableFuture<List<UserResponse>> saveUser(MultipartFile[] files) throws Exception {
        long start = System.currentTimeMillis();
        List<User> allUser = new ArrayList<>();
        for (MultipartFile file : files) {
            List<User> users = parseCSVFile(file);
            logger.info("saving list of users of size {}", users.size(), "" + Thread.currentThread().getName());
            allUser.addAll(users);
        }
        allUser = userRepository.saveAll(allUser);
        long end = System.currentTimeMillis();
        logger.info("Total time {}", (end - start));
        return CompletableFuture.completedFuture(mapper.usersToUserResponses(allUser));
    }

    @Async
    public CompletableFuture<List<UserResponse>> findAllUsers(){
        logger.info("get list of user by "+Thread.currentThread().getName());
        List<User> users=userRepository.findAll();
        return CompletableFuture.completedFuture(mapper.usersToUserResponses(users));
    }

    private List<User> parseCSVFile(final MultipartFile file) throws Exception {
        final List<User> users = new ArrayList<>();
        try {
            try (final BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
                String line;
                while ((line = br.readLine()) != null) {
                    final String[] data = line.split(",");
                    final User user = new User();
                    user.setUserName(data[0]);
                    user.setName(data[1]);
                    user.setSurname(data[2]);
                    users.add(user);
                    //kafkaTemplate.send(TOPIC,user.getUserName());
                }
                return users;
            }
        } catch (final IOException e) {
            logger.error("Failed to parse CSV file {}", e);
            throw new Exception("Failed to parse CSV file {}", e);
        }
    }
}
