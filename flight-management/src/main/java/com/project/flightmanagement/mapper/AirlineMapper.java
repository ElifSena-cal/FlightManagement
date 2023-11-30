package com.project.flightmanagement.mapper;
import com.project.flightmanagement.entity.Airline;
import com.project.flightmanagement.request.AirlineRequest;
import com.project.flightmanagement.response.AirlineResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import java.util.List;

@Mapper(componentModel = "spring")
public interface AirlineMapper {
    AirlineMapper INSTANCE = Mappers.getMapper(AirlineMapper.class);
    List<AirlineResponse> airlinesToAirlineResponse(List<Airline> airlines);
    AirlineResponse airlineToAirlineResponse(Airline airline);
    Airline airlineToAirlineResponse(AirlineResponse airlineResponse);
    AirlineRequest airlineToAirlineRequest(Airline airline);
    Airline airlineRequestToAirline(AirlineRequest airlineRequestRequest);
    void updateAirlineFromAirlineRequest(AirlineRequest airlineRequestRequest, @MappingTarget Airline existingAirline);
}
