package com.project.flightmanagement.mapper;

import com.project.flightmanagement.entity.Flight;
import com.project.flightmanagement.entity.Station;
import com.project.flightmanagement.request.FlightRequest;
import com.project.flightmanagement.request.StationRequest;
import com.project.flightmanagement.response.FlightResponse;
import com.project.flightmanagement.response.StationResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import java.util.List;
@Mapper(componentModel = "spring")
public interface FlightMapper {
    FlightMapper INSTANCE = Mappers.getMapper(FlightMapper.class);
    List<FlightResponse> flightsToFlightResponse(List<Flight> flights);
    FlightResponse flightToFlightResponse(Flight flight);
    Flight flightToFlightResponse(FlightResponse flightResponse);
    FlightRequest flightToFlightRequest(Flight station);

    Flight flightRequestToFlight(FlightRequest stationRequest);
    void updateFlightFromFlightRequest(FlightRequest flightRequest, @MappingTarget Flight existingFlight);
}
