package com.project.flightmanagement.entity;

import javax.persistence.*;

import com.sun.istack.NotNull;
import lombok.*;


import java.time.LocalDateTime;
@Entity
@Table(name = "airline")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Airline {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull    @Column(unique = true)
    private  String code;
    private  String description;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String updateUser;
}