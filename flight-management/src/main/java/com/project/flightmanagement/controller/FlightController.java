package com.project.flightmanagement.controller;
import com.project.flightmanagement.exception.EntityNotFoundException;
import com.project.flightmanagement.request.FlightRequest;
import com.project.flightmanagement.response.FlightResponse;
import com.project.flightmanagement.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/flights")
public class FlightController {
    private final FlightService flightService;

    @GetMapping
    public List<FlightResponse> listFlights() {
        return flightService.getAllFlights();
    }

    @PostMapping("/create")
    public FlightRequest createFlight(@RequestBody FlightRequest newFlight){
        return flightService.createFlight(newFlight);
    }

    @GetMapping("/{flightId}")
    public FlightResponse getFlightById(@PathVariable Long flightId) {
        return flightService.getFlightById(flightId);
    }

    @PutMapping("/update")
    public FlightRequest updateFlight( @RequestBody FlightRequest updatedFlight) throws DataIntegrityViolationException, EntityNotFoundException {
      return flightService.updateFlight(updatedFlight);
    }

    @DeleteMapping("/delete/{flightId}")
    public void deleteFlight(@PathVariable Long flightId) {
       flightService.deleteFlight(flightId);
    }
}
