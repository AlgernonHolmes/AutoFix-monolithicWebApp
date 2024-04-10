package com.example.autofix.autofix.services;


import com.example.autofix.autofix.entities.VehicleEntity;
import com.example.autofix.autofix.repositories.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class VehicleService {

    /* Repository layer methods */

    @Autowired
    VehicleRepository vehicleRepository;

    /* GET OPERATIONS */

    /*--------------------------------------------------------------------------------------------------------
     * getVehicles: method to find all vehicles in the DB;
     *
     * @return - a list with all vehicles;
     --------------------------------------------------------------------------------------------------------*/

    public ArrayList<VehicleEntity> getVehicles(){
        return (ArrayList<VehicleEntity>) vehicleRepository.findAll();
    }

    /*--------------------------------------------------------------------------------------------------------
     * getByPlate: method to find specific vehicle in DB by plate;
     *
     * @return - a vehicle with the specific plate;
     --------------------------------------------------------------------------------------------------------*/

    public VehicleEntity getByPlate(String rPlate){return (VehicleEntity) vehicleRepository.findByRegistrationPlate(rPlate);}

    /*--------------------------------------------------------------------------------------------------------
     * getAllByEngine: method to find all vehicles in DB with the same engineType;
     *
     * @return - all vehicles with said engineType;
     --------------------------------------------------------------------------------------------------------*/

    public ArrayList<VehicleEntity> getAllByEngine(String engineT){
        return (ArrayList<VehicleEntity>) vehicleRepository.findAllByEngineType(engineT);
    }




}
