package com.project.flightmanagement.entity;

import com.sun.istack.NotNull;
import lombok.*;


import javax.persistence.*;


import java.time.LocalDateTime;


@Entity
@Table(name = "station")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Station {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull    @Column(unique = true)
    private String code;
    private String description;
    private  LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String updateUser;
}
