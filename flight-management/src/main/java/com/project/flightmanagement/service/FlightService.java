package com.project.flightmanagement.service;
import com.project.flightmanagement.entity.Flight;
import com.project.flightmanagement.exception.EntityNotFoundException;
import com.project.flightmanagement.mapper.AircraftMapper;
import com.project.flightmanagement.mapper.AirlineMapper;
import com.project.flightmanagement.mapper.FlightMapper;
import com.project.flightmanagement.mapper.StationMapper;
import com.project.flightmanagement.repo.FlightRepository;
import com.project.flightmanagement.request.FlightRequest;
import com.project.flightmanagement.response.AircraftResponse;
import com.project.flightmanagement.response.AirlineResponse;
import com.project.flightmanagement.response.FlightResponse;
import com.project.flightmanagement.response.StationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FlightService {

    private final FlightRepository flightRepository;
    private final  AircraftService aircraftService;
    private final  AirlineService airlineService;
    private final StationService stationService;
    private final FlightMapper mapper;

    public List<FlightResponse> getAllFlights() {
        return mapper.flightsToFlightResponse(flightRepository.findAll());
    }

    public FlightRequest createFlight(FlightRequest flightRequest) {
        AirlineResponse airline = airlineService.getAirlineById(flightRequest.getAirlineId());
        AircraftResponse aircraft = aircraftService.getAircraftById(flightRequest.getAircraftId());
        StationResponse originStation = stationService.getStationById(flightRequest.getOriginStationId());
        StationResponse systemAirport = stationService.getStationById(flightRequest.getSystemAirportId());
        Flight flight = mapper.flightRequestToFlight(flightRequest);
        flight.setCreateTime(LocalDateTime.now());
        flight.setAirline(AirlineMapper.INSTANCE.airlineToAirlineResponse(airline));
        flight.setAircraft(AircraftMapper.INSTANCE.aircraftToAircraftResponse(aircraft));
        flight.setOriginStation(StationMapper.INSTANCE.stationToStationResponse(originStation));
        flight.setSystemAirport(StationMapper.INSTANCE.stationToStationResponse(systemAirport));
        if (isDuplicate(flight) > 0) {
            throw new DataIntegrityViolationException("Duplicate flight.");
        }
        return mapper.flightToFlightRequest(flightRepository.save(flight));
    }

    public FlightResponse getFlightById(Long flightId)  {
        return mapper.flightToFlightResponse(flightRepository.findById(flightId)
                .orElseThrow(() -> new EntityNotFoundException(flightId, "Flight")));
    }

    public FlightRequest updateFlight(FlightRequest updatedFlightRequest){
        Flight existingFlight = flightRepository.findById(updatedFlightRequest.getId()).orElseThrow(() ->   new EntityNotFoundException(updatedFlightRequest.getId(), "Flight"));
        AirlineResponse airline = airlineService.getAirlineById(updatedFlightRequest.getAirlineId());
        AircraftResponse aircraft = aircraftService.getAircraftById(updatedFlightRequest.getAircraftId());
        StationResponse originStation = stationService.getStationById(updatedFlightRequest.getOriginStationId());
        StationResponse systemAirport = stationService.getStationById(updatedFlightRequest.getSystemAirportId());
        mapper.updateFlightFromFlightRequest(updatedFlightRequest, existingFlight);
        existingFlight.setUpdateTime(LocalDateTime.now());
        existingFlight.setAirline(AirlineMapper.INSTANCE.airlineToAirlineResponse(airline));
        existingFlight.setAircraft(AircraftMapper.INSTANCE.aircraftToAircraftResponse(aircraft));
        existingFlight.setOriginStation(StationMapper.INSTANCE.stationToStationResponse(originStation));
        existingFlight.setSystemAirport(StationMapper.INSTANCE.stationToStationResponse(systemAirport));
        if (isDuplicate(existingFlight)>=2) {
            throw new DataIntegrityViolationException("Duplicate flight.");
        }
        return mapper.flightToFlightRequest(flightRepository.save(existingFlight));
    }

    private int isDuplicate(Flight updatedFlight) {
        int count = flightRepository.countByAirlineAndFlightNoAndFlightLegAndFlightDate(
                updatedFlight.getAirline(),
                updatedFlight.getFlightNo(),
                updatedFlight.getFlightLeg(),
                updatedFlight.getFlightDate()
        );
        return count;
    }

    public void deleteFlight(Long flightId)  {
        flightRepository.findById(flightId)
                .orElseThrow(() -> new EntityNotFoundException(flightId, "Flight"));
        flightRepository.deleteById(flightId);
    }

}
