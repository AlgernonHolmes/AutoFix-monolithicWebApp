package com.example.autofix.autofix.repositories;

import com.example.autofix.autofix.entities.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<VehicleEntity, Long> {

    public VehicleEntity findAllByRegistrationPlate(String registrationPlate);

    List<VehicleEntity> findByEngineType(String engineType);
}
