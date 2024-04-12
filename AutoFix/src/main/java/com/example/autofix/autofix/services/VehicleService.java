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

    /* POST OPERATIONS */

    /*--------------------------------------------------------------------------------------------------------
     * createVehicle: method to find create a vehicle in the DB;
     *
     * @return - null;
     --------------------------------------------------------------------------------------------------------*/

    public void createVehicle(VehicleEntity Vehicle){
        vehicleRepository.save(Vehicle);
    }

    /* UPDATE OPERATIONS */

    /*--------------------------------------------------------------------------------------------------------
     * updateVehicle: method to update a vehicle in the DB;
     *
     * @param updatedVehicle - the updated vehicle;
     --------------------------------------------------------------------------------------------------------*/

    public void updateVehicle(VehicleEntity updatedVehicle){
        vehicleRepository.save(updatedVehicle);
    }

    /* DELETE OPERATIONS */

    /*--------------------------------------------------------------------------------------------------------
     * deleteVehicle: method to delete a vehicle in the DB;
     *
     * @param id - the id of the vehicle to delete;
     * @return - null
     --------------------------------------------------------------------------------------------------------*/

    public void deleteVehicle(Long id){
        vehicleRepository.deleteById(id);
    }




}
