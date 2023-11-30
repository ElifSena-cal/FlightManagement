package com.project.flightmanagement.entity;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.flightmanagement.enums.Role;
import com.sun.istack.NotNull;
import lombok.*;


import java.io.Serializable;
import java.time.LocalDateTime;


@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;
    @NotNull
    @Column(unique = true)
    private String userName;
    private  String name;
    private String surname;
    @JsonIgnore
    private String password;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Role role;
    private String updateUser;
    private String email;
}

