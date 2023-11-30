package com.project.flightmanagement.repo;

import com.project.flightmanagement.entity.Aircraft;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AircraftRepository  extends JpaRepository<Aircraft,Long> {

}
