package com.project.flightmanagement.controller;
import com.project.flightmanagement.exception.EntityNotFoundException;
import com.project.flightmanagement.request.AircraftRequest;
import com.project.flightmanagement.response.AircraftResponse;
import com.project.flightmanagement.service.AircraftService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/aircrafts")
@RequiredArgsConstructor
public class AircraftController {
    private final AircraftService aircraftService;

    @GetMapping
    public List<AircraftResponse> listAircrafts() {
        return aircraftService.getAllAircrafts();
    }

    @PostMapping("/create")
    public AircraftRequest createAircraft(@RequestBody AircraftRequest newAircraft){
        return aircraftService.createAircraft(newAircraft);
    }

    @GetMapping("/{aircraftId}")
    public AircraftResponse getAircraftById(@PathVariable Long aircraftId) {
        return aircraftService.getAircraftById(aircraftId);
    }

    @PutMapping("/update")
    public AircraftRequest updateAircraft(@RequestBody AircraftRequest updatedAircraft) throws EntityNotFoundException {
        return  aircraftService.updateAircraft(updatedAircraft);
    }

    @DeleteMapping("/delete/{aircraftId}")
    public void deleteAircraft(@PathVariable Long aircraftId)  {
       aircraftService.deleteAircraft(aircraftId);
    }

    @GetMapping("/aircrafts-dropdown")
    public List<AircraftResponse> listAircraftResponseForDropdown() {
       return aircraftService.getAllAircrafts();
    }
}


