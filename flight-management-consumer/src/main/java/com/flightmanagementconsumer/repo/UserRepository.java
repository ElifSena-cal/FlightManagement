package com.flightmanagementconsumer.repo;

import com.flightmanagementconsumer.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User,Long> {
}
