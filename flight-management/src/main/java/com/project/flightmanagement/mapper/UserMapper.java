package com.project.flightmanagement.mapper;

import com.project.flightmanagement.entity.User;
import com.project.flightmanagement.request.UserRequest;
import com.project.flightmanagement.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    User userRequestToUser(UserRequest userRequest);
    UserRequest userToUserRequest(User user);
    void updateUserFromUserRequest(UserRequest updatedUser, @MappingTarget User existingUser);
    UserResponse userToUserResponse(User user);
    List<UserResponse> usersToUserResponses(List<User> users);

}
