package com.project.flightmanagement.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter

public class AircraftResponse {
    private Long id;
    private String code;
    private String description;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String updateUser;
}
