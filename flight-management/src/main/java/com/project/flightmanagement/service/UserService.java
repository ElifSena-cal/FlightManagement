package com.project.flightmanagement.service;

import com.project.flightmanagement.entity.User;
import com.project.flightmanagement.enums.Role;
import com.project.flightmanagement.exception.EntityNotFoundException;
import com.project.flightmanagement.mapper.UserMapper;
import com.project.flightmanagement.repo.UserRepository;
import com.project.flightmanagement.request.UserRequest;
import com.project.flightmanagement.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;


@Service
@RequiredArgsConstructor
@Transactional
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserMapper mapper;
    private final BCryptPasswordEncoder passwordEncoder =new BCryptPasswordEncoder();
    private final KafkaTemplate<String,String> kafkaTemplate;
    private final KeyCloakService keyCloakService;

    private static final String TOPIC = "NewTopic";
    public List<UserResponse> getAllUsers() {
        return mapper.usersToUserResponses(userRepository.findAll());
    }

    public UserRequest createUser(UserRequest newUser) {
            kafkaTemplate.send(TOPIC,newUser.getUserName());
            User user =  mapper.userRequestToUser(newUser);
            user.setPassword(passwordEncoder.encode(newUser.getPassword()));
            userRepository.save(user);
            keyCloakService.addUser(newUser);
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

}
