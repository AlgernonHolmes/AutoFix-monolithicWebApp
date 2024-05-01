package com.example.autofix.autofix.services;

import com.example.autofix.autofix.entities.RepairEntity;
import com.example.autofix.autofix.entities.VehicleEntity;
import com.example.autofix.autofix.repositories.RepairRepository;
import com.example.autofix.autofix.repositories.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
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

    public ArrayList<RepairEntity> getRepairs() {
        return (ArrayList<RepairEntity>) repairRepository.findAll();
    }

    /*--------------------------------------------------------------------------------------------------------
     * getRepairsByPlate: method to find all repairs in the DB with the correspondant plate;
     *
     * @param plate - the plate of the vehicle;
     * @return - a list with all repairs with said plate;
     --------------------------------------------------------------------------------------------------------*/

    public ArrayList<RepairEntity> getRepairsByPlate(String plate) {
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
    public int assignPrice(String repairType, String engineType) {
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

    public void createRepair(String plate, RepairEntity repair) {
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

    public void updateRepair(RepairEntity updatedRepair) {
        repairRepository.save(updatedRepair);
    }

    /* DELETE OPERATIONS */

    /*--------------------------------------------------------------------------------------------------------
     * deleteRepair: method to delete a repair in the DB;
     *
     * @param id - the id of the repair to delete;
     * @return - null
     --------------------------------------------------------------------------------------------------------*/

    public void deleteRepair(Long id) {
        repairRepository.deleteById(id);
    }





    /* Business layer methods */

    /* REPORTS */

    /* R2 */

    /*--------------------------------------------------------------------------------------------------------
     * indexVehicleType: method to determine the index of a vehicle type;
     *
     *
     * @param type - the type of vehicle;
     * @return - the index of the vehicle type;
     --------------------------------------------------------------------------------------------------------*/

    public int indexVehicleType(String type) {
        return switch (type.toLowerCase()) {
            case "sedan" -> 0;
            case "hatchback" -> 1;
            case "suv" -> 2;
            case "pickup" -> 3;
            case "furgoneta" -> 4;
            default -> -1;
        };
    }

    /*--------------------------------------------------------------------------------------------------------
     * repairTypeIndex: method to determine the index of a repair type;
     *
     *
     * @param repairType - the repair type;
     * @return - the index of the repair type;
     --------------------------------------------------------------------------------------------------------*/

    public int repairTypeIndex(String repairType) {
        return switch (repairType.toLowerCase()) {
            case "frenos" -> 0;
            case "refrigeracion" -> 1;
            case "motor" -> 2;
            case "transmision" -> 3;
            case "electrico" -> 4;
            case "escape" -> 5;
            case "neumaticos y ruedas" -> 6;
            case "suspension y la direccion" -> 7;
            case "aire acondicionado y calefaccion" -> 8;
            case "combustible" -> 9;
            case "parabrisas y cristales" -> 10;
            default -> -1;
        };
    }

    /*--------------------------------------------------------------------------------------------------------
     * repairTypeReport: method to generate a report of repair costs categorized by repair type and vehicle type.
     *
     * @return - a list of lists containing the total repair costs for each combination of repair type and
     *           vehicle type.
     --------------------------------------------------------------------------------------------------------*/
    /* R2 FUNCTION */
    public List<List<Double>> repairTypeReport(){
        int index_x, index_y;
        double originalValue;

        VehicleEntity vehicle;

        List<List<Double>> repairTypes = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            List<Double> innerList = new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                innerList.add(0.0);
            }
            repairTypes.add(innerList);
        }

        List<RepairEntity> DBrepairs = repairRepository.findAll();

        for(RepairEntity repair: DBrepairs){
            index_x = repairTypeIndex(repair.getRepairType().toLowerCase());
            vehicle = vehicleRepository.findByRegistrationPlate(repair.getVehiclePlate());
            index_y = indexVehicleType(vehicle.getType().toLowerCase());
            originalValue = repairTypes.get(index_x).get(index_y);
            repairTypes.get(index_x).set(index_y, (originalValue + repair.getTotalCost()));
        }

        return repairTypes;
    }


    /* R3 */

    /*--------------------------------------------------------------------------------------------------------
     * repairTime: method to calculate the duration of a repair in hours;
     *
     * @param entryDate - the entry date of the vehicle for repair;
     * @param entryTime - the entry time of the vehicle for repair;
     * @param exitDate - the exit date of the vehicle after repair;
     * @param exitTime - the exit time of the vehicle after repair;
     * @return - the duration of the repair in hours;
     --------------------------------------------------------------------------------------------------------*/
    public double repairTime(LocalDate entryDate, LocalTime entryTime, LocalDate exitDate, LocalTime exitTime){
        if (entryDate == null || entryTime == null || exitDate == null || exitTime == null) {
            return 0.0;
        }
        LocalDateTime entryDateTime = LocalDateTime.of(entryDate, entryTime);
        LocalDateTime exitDateTime = LocalDateTime.of(exitDate, exitTime);
        return (double) entryDateTime.until(exitDateTime, ChronoUnit.HOURS);
    }


    /*--------------------------------------------------------------------------------------------------------
     * allBrands: method to retrieve a list of unique vehicle brand names from a list of vehicles;
     *
     * @return - a list containing unique brand names of vehicles;
     --------------------------------------------------------------------------------------------------------*/
    public List<String> allBrands(){

        List<VehicleEntity> vehicles = vehicleRepository.findAll();
        List<String> allBrands = new ArrayList<>();
        String brandName = "";

        for(VehicleEntity vehicle : vehicles){
            brandName = vehicle.getBrand().toLowerCase();
            if(!allBrands.contains(brandName)){
                allBrands.add(brandName);
            }
        }
        return allBrands;
    }


    /*--------------------------------------------------------------------------------------------------------
     * repairTimeReport: method to generate a report of average repair times for each vehicle brand;
     *
     *
     * @return - a list containing the average repair time for each vehicle brand;
     --------------------------------------------------------------------------------------------------------*/
    /* R3 FUNCTION */
    public List<Double> repairTimeReport(){
        /* list with all avrg times*/
        List<Double> avrgRepairTime = new ArrayList<>();

        List<String> allBrands = allBrands();
        /* list to divide and to get average*/
        List<Double> timeDivision = new ArrayList<Double>();
        for(String brands : allBrands){
            timeDivision.add(1.0);
            avrgRepairTime.add(0.0);
        }

        int index = 0;
        List<RepairEntity> repairs = repairRepository.findAll();
        VehicleEntity vehicle;

        /* we iterate through all repairs and add all repairTimes
           associated with them; we get a list with all the total
           time of all brands, and another list with the amount of
           repairs associated with said brand; next, we will divide
           to get the average
         */
        for(RepairEntity repair : repairs){
            vehicle = vehicleRepository.findByRegistrationPlate(repair.getVehiclePlate());
            index = allBrands.indexOf(vehicle.getBrand().toLowerCase());
            timeDivision.set(index, (timeDivision.get(index) + 1.0) );
            avrgRepairTime.set(index, (avrgRepairTime.get(index) +
                    repairTime(repair.getEntryVDate(), repair.getEntryVTime(),
                               repair.getExitCDate(), repair.getExitVTime()))
                    );
        }

        /*
            now, we divide the total of the reparation times with the
            amount of times we added a repair; thus, we get a list with
            the average repair time for each brand;
         */
        for(int i = 0; i < allBrands.size(); i++){
            avrgRepairTime.set( i,
                    (avrgRepairTime.get(i) )/ (timeDivision.get(i))
                    );
        }


        return avrgRepairTime;
    }


    /* R4 */

    /*--------------------------------------------------------------------------------------------------------
     * indexEngineType: method to obtain the numerical index corresponding to a given engine type;
     *
     * @param engineType - the engine type for which the numerical index is desired;
     * @return - the numerical index corresponding to the engine type, or -1 if the engine type is not valid;
     --------------------------------------------------------------------------------------------------------*/
    public int indexEngineType(String engineType) {
        return switch (engineType.toLowerCase()) {
            case "gasolina" -> 0;
            case "diesel" -> 1;
            case "hibrido" -> 2;
            case "electrico" -> 3;
            default -> -1;
        };
    }


    /*--------------------------------------------------------------------------------------------------------
     * motorRepairReport: method to generate a report of repair statistics based on vehicle engine types and repair types;
     *
     * @return - a list of lists containing repair statistics for each vehicle engine and repair type;
     --------------------------------------------------------------------------------------------------------*/
    /* R4 FUNCTION */
    public List<List<Double>> motorRepairReport(){
        int repairIndex, brandIndex;
        VehicleEntity vehicle;
        List<RepairEntity> allRepairs = repairRepository.findAll();

        List<List<Double>> motorReport = new ArrayList<>();
        /* list initial values */
        for (int i = 0; i < 11; i++) {
            List<Double> innerList = new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                innerList.add(0.0);
            }
            motorReport.add(innerList);
        }


        for(RepairEntity repair : allRepairs) {

            vehicle = vehicleRepository.findByRegistrationPlate(repair.getVehiclePlate());
            String repairType = repair.getRepairType().toLowerCase();
            repairIndex = repairTypeIndex(repairType);
            brandIndex = indexEngineType(vehicle.getEngineType().toLowerCase());

            motorReport.get(repairIndex).set(brandIndex,
                    (motorReport.get(repairIndex).get(brandIndex) + 1)
                    );
            motorReport.get(repairIndex).set(4,
                    (motorReport.get(repairIndex).get(4) + repair.getTotalCost())
                    );
        }
        return motorReport;
    }

}
