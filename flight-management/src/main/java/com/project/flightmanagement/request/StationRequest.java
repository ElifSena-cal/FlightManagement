package com.project.flightmanagement.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StationRequest {
    private Long id;
    private String code;
    private String description;
    private String updateUser;
}
