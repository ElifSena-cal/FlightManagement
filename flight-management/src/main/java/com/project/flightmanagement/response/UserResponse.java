package com.project.flightmanagement.response;
import com.project.flightmanagement.enums.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class UserResponse {
    private Long id;
    private String userName;
    private  String name;
    private String surname;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Role role;
    private String UpdateUser;
}
