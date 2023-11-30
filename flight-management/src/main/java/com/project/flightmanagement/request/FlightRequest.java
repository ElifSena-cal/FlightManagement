package com.project.flightmanagement.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class FlightRequest {
    private Long id;
    private Long airlineId;
    private String flightNo;
    private Long aircraftId;
    private String flightLeg;
    private LocalDateTime flightDate;
    private Long systemAirportId;
    private Long originStationId;
    private String updateUser;
}
