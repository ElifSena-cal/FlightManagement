package com.project.flightmanagement.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AircraftRequest {
    private  Long id;
    private String code;
    private String description;
    private String updateUser;
}
