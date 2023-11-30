package com.project.flightmanagement.service;
import com.project.flightmanagement.entity.Aircraft;
import com.project.flightmanagement.exception.EntityNotFoundException;
import com.project.flightmanagement.mapper.AircraftMapper;
import com.project.flightmanagement.repo.AircraftRepository;
import com.project.flightmanagement.request.AircraftRequest;
import com.project.flightmanagement.response.AircraftResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.cache.annotation.Cacheable;

@Service
@RequiredArgsConstructor
@Transactional
public class AircraftService {
    private final AircraftRepository aircraftRepository;
    private final AircraftMapper mapper;
    @Cacheable(value = "flightmanagement" , key = "'allAircrafts'")
    public List<AircraftResponse> getAllAircrafts() {
        System.out.println("Worked");
        return mapper.aircraftToAircraftResponse(aircraftRepository.findAll());
    }


    public AircraftRequest createAircraft(AircraftRequest newAircraft) {
        Aircraft createdAircraft = mapper.aircraftRequestToAircraft(newAircraft);

        if (createdAircraft == null) {
            throw new RuntimeException("Aircraft creation failed: mapper.aircraftRequestToAircraft returned null.");
        }
        createdAircraft.setCreateTime(LocalDateTime.now());
        aircraftRepository.save(createdAircraft);
        return mapper.aircraftToAircraftRequest(createdAircraft);
    }

    public AircraftResponse getAircraftById(Long aircraftId) {
        return mapper.aircraftToAircraftResponse(aircraftRepository.findById(aircraftId)
                .orElseThrow(() -> new EntityNotFoundException(aircraftId, "User")));
    }

    public AircraftRequest updateAircraft(AircraftRequest updatedAircraft){
        Aircraft existingAircraft = aircraftRepository.findById(updatedAircraft.getId()).orElseThrow(() -> new EntityNotFoundException(updatedAircraft.getId(),"Aircraft"));
        mapper.updateAircraftFromAircraftRequest(updatedAircraft, existingAircraft);
        existingAircraft.setUpdateTime(LocalDateTime.now());
        aircraftRepository.save(existingAircraft);
        return mapper.aircraftToAircraftRequest(aircraftRepository.save(existingAircraft));
    }

    public void deleteAircraft(Long aircraftId) {
        aircraftRepository.findById(aircraftId)
                .orElseThrow(() -> new EntityNotFoundException(aircraftId, "Aircraft"));
        aircraftRepository.deleteById(aircraftId);
    }

}
