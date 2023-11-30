package com.project.flightmanagement.mapper;
import com.project.flightmanagement.entity.Station;
import com.project.flightmanagement.entity.User;
import com.project.flightmanagement.request.StationRequest;
import com.project.flightmanagement.request.UserRequest;
import com.project.flightmanagement.response.StationResponse;
import com.project.flightmanagement.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StationMapper {
    StationMapper INSTANCE = Mappers.getMapper(StationMapper.class);
    List<StationResponse> stationsToStationResponse(List<Station> stations);
    StationResponse stationToStationResponse(Station station);
    Station stationToStationResponse(StationResponse stationResponse);
    StationRequest stationToStationRequest(Station station);
    Station stationRequestToStation(StationRequest stationRequest);
    void updateStationFromStationRequest(StationRequest stationRequest, @MappingTarget Station existingStation);
}
