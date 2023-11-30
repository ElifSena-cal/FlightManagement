package com.project.flightmanagement.request;

import com.project.flightmanagement.enums.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {
    private  Long id;
    private String userName;
    private  String name;
    private String surname;
    private String password;
    private Role role;
    private String updateUser;
    private String email;
}
