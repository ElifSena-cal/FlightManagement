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
    private int messageCounter = 0;
    private long startTime;

    @KafkaListener(topics = "NewTopic", groupId = "group_id", concurrency = "3")
    public void consume(String message) {
       userService.SaveUser(message);
        messageCounter++;
        startTime = System.currentTimeMillis();
        System.out.println(messageCounter);
        if (messageCounter == 1001) {
            long endTime = System.currentTimeMillis();
            long elapsedTime = endTime - startTime;
            System.out.println("All messages processed in " + elapsedTime + " milliseconds.");
        }
    }

}
