package com.project.flightmanagement.mapper;

import com.project.flightmanagement.entity.Aircraft;
import com.project.flightmanagement.request.AircraftRequest;
import com.project.flightmanagement.response.AircraftResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;
@Mapper(componentModel = "spring")
public interface AircraftMapper {
    AircraftMapper INSTANCE = Mappers.getMapper(AircraftMapper.class);
    List<AircraftResponse> aircraftToAircraftResponse(List<Aircraft> aircraft);
    AircraftResponse aircraftToAircraftResponse(Aircraft aircraft);
    Aircraft  aircraftToAircraftResponse(AircraftResponse aircraftResponse);
    AircraftRequest aircraftToAircraftRequest(Aircraft aircraft);
    Aircraft aircraftRequestToAircraft(AircraftRequest aircraftRequestRequest);
    void updateAircraftFromAircraftRequest(AircraftRequest aircraftRequestRequest, @MappingTarget Aircraft existingAircraft);
}
