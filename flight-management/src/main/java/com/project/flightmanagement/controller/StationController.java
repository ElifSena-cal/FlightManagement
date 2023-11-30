package com.project.flightmanagement.controller;

import com.project.flightmanagement.exception.EntityNotFoundException;
import com.project.flightmanagement.request.StationRequest;
import com.project.flightmanagement.response.StationResponse;
import com.project.flightmanagement.service.StationService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stations")
@RequiredArgsConstructor
public class StationController {
    private final StationService stationService;


    @GetMapping
    public List<StationResponse> listStations() {
        return stationService.getAllStations();
    }

    @PostMapping("/create")
    public StationRequest createStation(@RequestBody StationRequest newStation) throws DataIntegrityViolationException {
      return stationService.createStation(newStation);
    }

    @GetMapping("/{stationId}")
    public StationResponse getStationById(@PathVariable Long stationId) {
       return stationService.getStationById(stationId);
    }

    @PutMapping("/update")
    public StationRequest updateStation(@RequestBody StationRequest updatedStation) throws EntityNotFoundException {
       return stationService.updateStation(updatedStation);
    }

    @DeleteMapping("/delete/{stationId}")
    public void deleteStation(@PathVariable Long stationId) {
         stationService.deleteStation(stationId);
    }

    @GetMapping("/stations-dropdown")
    public List<StationResponse> listStationResponseForDropdown() {
        return stationService.getAllStations();
    }

}
