package com.example.autofix.autofix.repositories;

import com.example.autofix.autofix.entities.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<VehicleEntity, Long> {

    public VehicleEntity findByRegistrationPlate(String registrationPlate);

    @Query("SELECT v FROM VehicleEntity v LEFT JOIN FETCH v.idRepair WHERE v.registrationPlate = :plate")
    VehicleEntity findByRegistrationPlateWithRepairs(@Param("plate") String plate);

    @Transactional
    public void deleteByRegistrationPlate(String registrationPlate);




}
