package com.project.flightmanagement.controller;
import com.project.flightmanagement.exception.EntityNotFoundException;
import com.project.flightmanagement.request.AirlineRequest;
import com.project.flightmanagement.response.AirlineResponse;
import com.project.flightmanagement.service.AirlineService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/airlines")
@RequiredArgsConstructor
public class AirlineController {
    private final AirlineService airlineService;

    @GetMapping
    public List<AirlineResponse> listAirlines() {
        return airlineService.getAllAirlines();
    }

    @PostMapping("/create")
    public AirlineRequest createAirline(@RequestBody AirlineRequest newAirline)  {
      return airlineService.createAirline(newAirline);
    }

    @GetMapping("/{airlineId}")
    public AirlineResponse getAirlineById(@PathVariable Long airlineId) {
        return airlineService.getAirlineById(airlineId);
    }

    @PutMapping("/update")
    public AirlineRequest updateAirline(@RequestBody AirlineRequest updatedAirline) throws EntityNotFoundException {
        return airlineService.updateAirline(updatedAirline);
    }

    @DeleteMapping("/delete/{airlineId}")
    public void deleteAirline(@PathVariable Long airlineId)  {
        airlineService.deleteAirline(airlineId);
    }

    @GetMapping("/airlines-dropdown")
    public List<AirlineResponse> listAirlineResponseForDropdown() {
        return airlineService.getAllAirlines();
    }

}
