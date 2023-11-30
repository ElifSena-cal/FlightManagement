package com.project.flightmanagement.response;

import com.project.flightmanagement.entity.Flight;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class FlightResponse {
    private Long id;
    private Long airlineId;
    private String flightNo;
    private Long aircraftId;
    private String flightLeg;
    private LocalDateTime flightDate;
    private Long systemAirportId;
    private Long originStationId;
    private String updateUser;
    private LocalDateTime updateTime;
}
