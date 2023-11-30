package com.flightmanagementconsumer.consumer;

import com.flightmanagementconsumer.entity.User;
import com.flightmanagementconsumer.repo.UserRepository;
import com.flightmanagementconsumer.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaConsumer {
    private final UserService userService;
    @KafkaListener(topics = "NewTopic", groupId = "group_id")
    public void consume(String message) {
        userService.SaveUser(message);
        System.out.println("message = " + message);
    }
}