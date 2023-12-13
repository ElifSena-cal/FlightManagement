package com.project.flightmanagement.repo;
import com.project.flightmanagement.entity.Station;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StationRepository extends JpaRepository<Station,Long> {

}
