package com.project.flightmanagement.service;

import com.project.flightmanagement.entity.Airline;
import com.project.flightmanagement.exception.EntityNotFoundException;
import com.project.flightmanagement.mapper.AirlineMapper;
import com.project.flightmanagement.repo.AirlineRepository;
import com.project.flightmanagement.request.AirlineRequest;
import com.project.flightmanagement.response.AirlineResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AirlineService {
    private final AirlineRepository airlineRepository;
    private final AirlineMapper mapper;
    public List<AirlineResponse> getAllAirlines() {
        return mapper.airlinesToAirlineResponse(airlineRepository.findAll());
    }

    public AirlineRequest createAirline(AirlineRequest newAirline)  {
        Airline createdAirline = mapper.airlineRequestToAirline(newAirline);
        if (createdAirline == null) {
            throw new RuntimeException("Airline creation failed: mapper.airlineRequestToAirline returned null.");
        }
        createdAirline.setCreateTime(LocalDateTime.now());
        airlineRepository.save(createdAirline);
        return mapper.airlineToAirlineRequest(createdAirline);
    }

    public AirlineResponse getAirlineById(Long airlineId)  {
        return mapper.airlineToAirlineResponse(airlineRepository.findById(airlineId)
                .orElseThrow(() -> new EntityNotFoundException(airlineId, "User")));
    }

    public AirlineRequest updateAirline( AirlineRequest updatedAirline) {
        Airline existingAirline = airlineRepository.findById(updatedAirline.getId()).orElseThrow(() ->   new EntityNotFoundException(updatedAirline.getId(), "Airline"));
        mapper.updateAirlineFromAirlineRequest(updatedAirline, existingAirline);
        existingAirline.setUpdateTime(LocalDateTime.now());
        return mapper.airlineToAirlineRequest(airlineRepository.save(existingAirline));
    }

    public void deleteAirline(Long airlineId) {
        airlineRepository.findById(airlineId)
                .orElseThrow(() -> new EntityNotFoundException(airlineId, "Airline"));

        airlineRepository.deleteById(airlineId);
    }


}
