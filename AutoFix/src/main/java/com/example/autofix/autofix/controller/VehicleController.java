package com.example.autofix.autofix.controller;


import com.example.autofix.autofix.entities.VehicleEntity;
import com.example.autofix.autofix.services.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/vehicle")
@CrossOrigin("*")
public class VehicleController {

    /* API endpoints */

    /* Service layer methods */

    @Autowired
    VehicleService vehicleService;

    /* GET OPERATIONS */

    /*--------------------------------------------------------------------------------------------------------
     * getVehicles: method to find all vehicles in the DB;
     *
     * @return - a list with all vehicles;
     --------------------------------------------------------------------------------------------------------*/

    @GetMapping("/")
    public List<VehicleEntity> getAllVehicles() {
        return (List<VehicleEntity>) vehicleService.getVehicles();

    }

    /*--------------------------------------------------------------------------------------------------------
     * getByPlate: method to find specific vehicle in DB by plate;
     *
     * @return - a vehicle with the specific plate;
     --------------------------------------------------------------------------------------------------------*/
    @GetMapping("/plate/{registrationPlate}")
    public VehicleEntity getVehicleByPlate(@PathVariable String registrationPlate) {
        return vehicleService.getByPlate(registrationPlate);
    }

    /* POST OPERATIONS */

    /*--------------------------------------------------------------------------------------------------------
     * createVehicle: method to find create a vehicle in the DB;
     *
     * @return - null;
     --------------------------------------------------------------------------------------------------------*/
    @PostMapping("/")
    public void createVehicle(@RequestBody VehicleEntity vehicle) {
        vehicleService.createVehicle(vehicle);
    }

    /* UPDATE OPERATIONS */

    /*--------------------------------------------------------------------------------------------------------
     * updateVehicle: method to update a vehicle in the DB;
     *
     * @param id - the id of the vehicle to be updated;
     * @param updatedVehicle - the updated vehicle object;
     --------------------------------------------------------------------------------------------------------*/
    @PutMapping("/{id}")
    public void updateVehicle(@PathVariable Long id, @RequestBody VehicleEntity updatedVehicle) {
        updatedVehicle.setId(id);
        vehicleService.updateVehicle(updatedVehicle);
    }

    /*--------------------------------------------------------------------------------------------------------
     * updateVehicle: method to update a vehicle in the DB;
     *
     * @param updatedVehicle - the updated vehicle;
     --------------------------------------------------------------------------------------------------------*/
    @PutMapping("/")
    public void updateVehicle(@RequestBody VehicleEntity updatedVehicle) {
        vehicleService.updateVehicle(updatedVehicle);
    }

    /* DELETE OPERATIONS */

    /*--------------------------------------------------------------------------------------------------------
     * deleteVehicle: method to delete a vehicle in the DB;
     *
     * @param id - the id of the vehicle to delete;
     * @return - null
     --------------------------------------------------------------------------------------------------------*/
    @DeleteMapping("/{id}")
    public void deleteVehicle(@PathVariable Long id) {
        vehicleService.deleteVehicle(id);
    }

    /*--------------------------------------------------------------------------------------------------------
     * deleteVehicleByPlate: method to delete a vehicle in the DB;
     *
     * @param plate - the plate of the vehicle to delete;
     * @return - null
     --------------------------------------------------------------------------------------------------------*/

    @DeleteMapping("/plate/{plate}")
    public void deleteVehicleByPlate(@PathVariable String plate){
        vehicleService.deleteVehicleByPlate(plate);
    }

}
