package com.example.autofix.autofix.services;

import com.example.autofix.autofix.entities.RepairEntity;
import com.example.autofix.autofix.entities.VehicleEntity;
import com.example.autofix.autofix.repositories.RepairRepository;
import com.example.autofix.autofix.repositories.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RepairService {

    /* Repository layer methods */

    @Autowired
    RepairRepository repairRepository;
    @Autowired
    VehicleRepository vehicleRepository;

    /* GET OPERATIONS */

    /*--------------------------------------------------------------------------------------------------------
     * getRepairs: method to find all repairs in the DB;
     *
     * @return - a list with all repairs;
     --------------------------------------------------------------------------------------------------------*/

    public ArrayList<RepairEntity> getRepairs(){
        return (ArrayList<RepairEntity>) repairRepository.findAll();
    }


    /* POST OPERATIONS */

    /*--------------------------------------------------------------------------------------------------------
     * createRepair: method to find create a repair in the DB;
     *
     * @Param - plate: the registration plate of the vehicle;
     * @Param - repair: the repair to be saved in the DB;
     * @return - null;
     --------------------------------------------------------------------------------------------------------*/

    public void createRepair(String plate, RepairEntity repair){
        RepairEntity savedRepair = repairRepository.save(repair);
        Long rId = savedRepair.getId();
        VehicleEntity lVehicle = vehicleRepository.findByRegistrationPlateWithRepairs(plate);
        List<Long> vehicleRepairList = lVehicle.getIdRepair();
        vehicleRepairList.add(rId);
        lVehicle.setIdRepair(vehicleRepairList);
        vehicleRepository.save(lVehicle);
    }

    /* UPDATE OPERATIONS */

    /*--------------------------------------------------------------------------------------------------------
     * updateRepair: method to update a repair in the DB;
     *
     * @Param - repair: the repair to be updated in the DB;
     * @return - null;
     --------------------------------------------------------------------------------------------------------*/

    public void updateRepair(RepairEntity updatedRepair){
        repairRepository.save(updatedRepair);
    }

    /* DELETE OPERATIONS */

    /*--------------------------------------------------------------------------------------------------------
     * deleteRepair: method to delete a repair in the DB;
     *
     * @param id - the id of the repair to delete;
     * @return - null
     --------------------------------------------------------------------------------------------------------*/

    public void deleteRepair(Long id){
        repairRepository.deleteById(id);
    }

}
