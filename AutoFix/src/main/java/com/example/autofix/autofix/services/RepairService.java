package com.example.autofix.autofix.services;

import com.example.autofix.autofix.entities.RepairEntity;
import com.example.autofix.autofix.entities.VehicleEntity;
import com.example.autofix.autofix.repositories.RepairRepository;
import com.example.autofix.autofix.repositories.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
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



    /* SURCHARGES */

    /*--------------------------------------------------------------------------------------------------------
     * antiquitySurcharge: method to calculate antiquity surcharge;
     *
     * @param price - repair price;
     * @param fabricationYear - fabrication year;
     * @param type - vehicle type;
     * @return - the amount to be surcharged;
     --------------------------------------------------------------------------------------------------------*/

    public double antiquitySurcharge(double price, int fabricationYear, String type){

        int antiquity = 2024 - fabricationYear;
        double surchargePercentage = 0.0;

        if (antiquity >= 6 && antiquity <= 10) {
            if (type.equals("sedan") || type.equals("hatchback")) {
                surchargePercentage = 0.05;
            } else {
                surchargePercentage = 0.07;
            }
        } else if (antiquity >= 11 && antiquity <= 15) {
            if (type.equals("sedan") || type.equals("hatchback")) {
                surchargePercentage = 0.09;
            } else {
                surchargePercentage = 0.11;
            }
        } else if (antiquity >= 16) {
            if (type.equals("sedan") || type.equals("hatchback")) {
                surchargePercentage = 0.15;
            } else {
                surchargePercentage = 0.20;
            }
        }

        double surchargeValue = price * surchargePercentage;


        return surchargeValue;

    }


    /*--------------------------------------------------------------------------------------------------------
     * delaySurcharge: method to calculate antiquity surcharge;
     *
     * @param price - repair price;
     * @param exitVDate - date given to take the vehicle;
     * @param exitCDate - date the customer took the vehicle;
     * @return - the amount to be surcharged;
     --------------------------------------------------------------------------------------------------------*/
    public double delaySurcharge(double price, LocalDate exitVDate, LocalDate exitCDate) {
        long diffInDays = ChronoUnit.DAYS.between(exitVDate, exitCDate);
        if (diffInDays <= 0) {
            return price;
        }
        double delaySurchargePercentage = 0.05;
        double delaySurcharge = price * diffInDays * delaySurchargePercentage;
        return delaySurcharge;
    }


    /*--------------------------------------------------------------------------------------------------------
     * mileageSurcharge: method to calculate mileage surcharge;
     *
     * @param price - repair price;
     * @param mileage - mileage of the vehicle;
     * @param type - type of the vehicle;
     * @return - the amount to be surcharged;
     --------------------------------------------------------------------------------------------------------*/
    public double mileageSurcharge(double price, int mileage, String type) {
        double surchargePercentage = 0.0;

        if (mileage >= 5001 && mileage <= 12000) {
            if (type.equals("sedan") || type.equals("hatchback")) {
                surchargePercentage = 0.03;
            } else {
                surchargePercentage = 0.05;
            }
        } else if (mileage >= 12001 && mileage <= 25000) {
            if (type.equals("sedan") || type.equals("hatchback")) {
                surchargePercentage = 0.07;
            } else {
                surchargePercentage = 0.09;
            }
        } else if (mileage >= 25001 && mileage <= 40000) {
            surchargePercentage = 0.12;
        } else {
            surchargePercentage = 0.20;
        }

        double surchargeValue = price * surchargePercentage;
        return surchargeValue;
    }

    /* DISCOUNTS */


    /*--------------------------------------------------------------------------------------------------------
     * dayDiscount: method to calculate day discount;
     *
     * @param price      - repair price;
     * @param entryVDate - date of vehicle entry to the workshop;
     * @param entryVTime - time of vehicle entry to the workshop;
     * @return - the amount to be discounted;
     --------------------------------------------------------------------------------------------------------*/

    public double dayDiscount(double price, LocalDate entryVDate, LocalTime entryVTime) {
        if ((entryVDate.getDayOfWeek() == DayOfWeek.MONDAY || entryVDate.getDayOfWeek() == DayOfWeek.THURSDAY)
                && entryVTime.isAfter(LocalTime.of(9, 0)) && entryVTime.isBefore(LocalTime.of(12, 0))) {
            return price * 0.1;
        } else {
            return 0;
        }
    }


    /*--------------------------------------------------------------------------------------------------------
     * repAmountDiscount: method to calculate repair amount discount;
     *
     * @param plate      - vehicle plate number;
     * @param engineType - type of engine (gasoline, diesel, hybrid, electric);
     * @return - the discount percentage applied based on the repair quantity and engine type;
     --------------------------------------------------------------------------------------------------------*/
    public double repAmountDiscount(String plate, String engineType){
        List<RepairEntity> repairs = repairRepository.findAllByVehiclePlate(plate);
        LocalDate currentDate = LocalDate.now();
        int repairQuantity = 0;
        for (RepairEntity repair : repairs) {
            if (ChronoUnit.MONTHS.between(repair.getEntryVDate(), currentDate) <= 12) {
                repairQuantity = repairQuantity + 1;
            }
        }

        double discount = 0;

        if (repairQuantity >= 1 && repairQuantity <= 2) {
            if (engineType.equalsIgnoreCase("gasolina")) {
                discount = 0.05;
            } else if (engineType.equalsIgnoreCase("diesel")) {
                discount = 0.07;
            } else if (engineType.equalsIgnoreCase("hibrido")) {
                discount = 0.10;
            } else if (engineType.equalsIgnoreCase("electrico")) {
                discount = 0.08;
            }
        } else if (repairQuantity >= 3 && repairQuantity <= 5) {
            if (engineType.equalsIgnoreCase("gasolina")) {
                discount = 0.10;
            } else if (engineType.equalsIgnoreCase("diesel")) {
                discount = 0.12;
            } else if (engineType.equalsIgnoreCase("hibrido")) {
                discount = 0.15;
            } else if (engineType.equalsIgnoreCase("electrico")) {
                discount = 0.13;
            }
        } else if (repairQuantity >= 6 && repairQuantity <= 9) {
            if (engineType.equalsIgnoreCase("gasolina")) {
                discount = 0.15;
            } else if (engineType.equalsIgnoreCase("diesel")) {
                discount = 0.17;
            } else if (engineType.equalsIgnoreCase("hibrido")) {
                discount = 0.20;
            } else if (engineType.equalsIgnoreCase("electrico")) {
                discount = 0.18;
            }
        } else if (repairQuantity >= 10) {
            if (engineType.equalsIgnoreCase("gasolina")) {
                discount = 0.20;
            } else if (engineType.equalsIgnoreCase("diesel")) {
                discount = 0.22;
            } else if (engineType.equalsIgnoreCase("hibrido")) {
                discount = 0.25;
            } else if (engineType.equalsIgnoreCase("electrico")) {
                discount = 0.23;
            }
        }
        return discount;
    }



}
