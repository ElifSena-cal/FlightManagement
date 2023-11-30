package com.project.flightmanagement.response;


import com.project.flightmanagement.enums.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponse {
   private String accessToken;
   private Role role;
   private String userName;

}
