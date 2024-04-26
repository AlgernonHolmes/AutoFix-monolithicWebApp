package com.example.autofix.autofix.services;

import com.example.autofix.autofix.entities.RepairEntity;
import com.example.autofix.autofix.entities.VehicleEntity;
import com.example.autofix.autofix.repositories.RepairRepository;
import com.example.autofix.autofix.repositories.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
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

    /*--------------------------------------------------------------------------------------------------------
     * getRepairsByPlate: method to find all repairs in the DB with the correspondant plate;
     *
     * @param plate - the plate of the vehicle;
     * @return - a list with all repairs with said plate;
     --------------------------------------------------------------------------------------------------------*/
    public ArrayList<RepairEntity> getRepairsByPlate(String plate){
        return (ArrayList<RepairEntity>) repairRepository.findAllByVehiclePlate(plate);
    }


    /* POST OPERATIONS */

    /* auxiliary method */

    /*--------------------------------------------------------------------------------------------------------
     * assignPrice: method to assign a price to a repair;
     *
     * @param repairType - the repairType;
     * @param engineType - the engineType;
     * @return - the price assigned;
     --------------------------------------------------------------------------------------------------------*/
    public int assignPrice(String repairType, String engineType){
        List<List<Integer>> prices = new ArrayList<>();
        prices.add(List.of(120000, 120000, 180000, 220000));
        prices.add(List.of(130000, 130000, 190000, 230000));
        prices.add(List.of(350000, 450000, 700000, 800000));
        prices.add(List.of(210000, 210000, 300000, 300000));
        prices.add(List.of(150000, 150000, 200000, 250000));
        prices.add(List.of(100000, 120000, 450000, 0));
        prices.add(List.of(100000, 100000, 100000, 100000));
        prices.add(List.of(180000, 180000, 210000, 250000));
        prices.add(List.of(150000, 150000, 180000, 180000));
        prices.add(List.of(130000, 140000, 220000, 0));
        prices.add(List.of(80000, 80000, 80000, 80000));

        /* axis to find the correspondant price in the table */
        int axis_x, axis_y;

        /* axis x value assignation for repairType */
        switch (repairType.toLowerCase()) {
            case "frenos":
                axis_x = 0;
                break;
            case "refrigeracion":
                axis_x = 1;
                break;
            case "motor":
                axis_x = 2;
                break;
            case "transmision":
                axis_x = 3;
                break;
            case "electrico":
                axis_x = 4;
                break;
            case "escape":
                axis_x = 5;
                break;
            case "neumaticos y ruedas":
                axis_x = 6;
                break;
            case "suspension y la direccion":
                axis_x = 7;
                break;
            case "aire acondicionado y calefaccion":
                axis_x = 8;
                break;
            case "combustible":
                axis_x = 9;
                break;
            case "parabrisas y cristales":
                axis_x = 10;
                break;
            default:
                axis_x = -1;
                break;
        }

        /* axis y value assignation for engineType */
        switch (engineType.toLowerCase()) {
            case "gasolina":
                axis_y = 0;
                break;
            case "diesel":
                axis_y = 1;
                break;
            case "hibrido":
                axis_y = 2;
                break;
            case "electrico":
                axis_y = 3;
                break;
            default:
                axis_y = -1;
                break;
        }


        int newPrice = prices.get(axis_x).get(axis_y);
        return newPrice;
    }

    /*--------------------------------------------------------------------------------------------------------
     * createRepair: method to find create a repair in the DB;
     *
     * @Param - plate: the registration plate of the vehicle;
     * @Param - repair: the repair to be saved in the DB;
     * @return - null;
     --------------------------------------------------------------------------------------------------------*/

    public void createRepair(String plate, RepairEntity repair){
        VehicleEntity lVehicle = vehicleRepository.findByRegistrationPlateWithRepairs(plate);
        int price = assignPrice(repair.getRepairType(), lVehicle.getEngineType());
        repair.setTotalCost(price);
        repair.setVehiclePlate(plate);
        RepairEntity savedRepair = repairRepository.save(repair);
        Long rId = savedRepair.getId();
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


    /* Business layer methods */







}
