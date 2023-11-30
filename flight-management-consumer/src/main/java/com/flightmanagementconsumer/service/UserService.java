package com.flightmanagementconsumer.service;

import com.flightmanagementconsumer.entity.User;
import com.flightmanagementconsumer.repo.UserRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public void SaveUser(String userName){
        User user=new User();
        user.setUserName(userName);
        userRepository.save(user);
    }
}
