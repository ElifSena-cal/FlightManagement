package com.project.flightmanagement.service;
import com.project.flightmanagement.entity.Station;
import com.project.flightmanagement.exception.EntityNotFoundException;
import com.project.flightmanagement.mapper.StationMapper;
import com.project.flightmanagement.repo.StationRepository;
import com.project.flightmanagement.request.StationRequest;
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
public class StationService {
    private final StationRepository stationRepository;
    private final StationMapper mapper;
    public List<StationResponse> getAllStations() {
        return mapper.stationsToStationResponse(stationRepository.findAll());
    }
    public StationRequest createStation(StationRequest newStation) throws DataIntegrityViolationException {
        Station station =  mapper.stationRequestToStation(newStation);
        station.setCreateTime(LocalDateTime.now());
        stationRepository.save(station);
        return mapper.stationToStationRequest(station);
    }
    public StationResponse getStationById(Long stationId) {
        return mapper.stationToStationResponse(stationRepository.findById(stationId)
                .orElseThrow(() -> new EntityNotFoundException(stationId, "User")));
    }
    public StationRequest updateStation(StationRequest updatedStation)  {
        Station existingStation = stationRepository.findById(updatedStation.getId()).orElseThrow(() -> new EntityNotFoundException(updatedStation.getId(),"Station"));
        mapper.updateStationFromStationRequest(updatedStation, existingStation);
        existingStation.setUpdateTime(LocalDateTime.now());
        return mapper.stationToStationRequest(stationRepository.save(existingStation));
    }
    public void deleteStation(Long stationId) {
        stationRepository.findById(stationId)
                .orElseThrow(() -> new EntityNotFoundException(stationId, "Station"));
        stationRepository.deleteById(stationId);

    }
}
