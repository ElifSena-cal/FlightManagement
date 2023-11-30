package com.project.flightmanagement.response;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class StationResponse {
    private Long id;
    private String code;
    private String description;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String updateUser;
}
