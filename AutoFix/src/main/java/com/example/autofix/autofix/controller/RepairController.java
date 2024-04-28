package com.example.autofix.autofix.controller;

import com.example.autofix.autofix.entities.RepairEntity;
import com.example.autofix.autofix.services.RepairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/repair")
@CrossOrigin("*")
public class RepairController {

    /* API endpoints */

    /* Service layer methods */

    @Autowired
    RepairService repairService;

    /* GET OPERATIONS */

    /*--------------------------------------------------------------------------------------------------------
     * getRepairs: method to find all repairs in the DB;
     *
     * @return - a list with all repairs;
     --------------------------------------------------------------------------------------------------------*/

    @GetMapping("/")
    public List<RepairEntity> getAllRepairs() {
        return repairService.getRepairs();
    }

    /*--------------------------------------------------------------------------------------------------------
     * getRepairsByPlate: method to find all repairs in the DB with the correspondant plate;
     *
     * @param plate - the plate of the vehicle;
     * @return - a list with all repairs with said plate;
     --------------------------------------------------------------------------------------------------------*/
    @GetMapping("/{plate}")
    public List<RepairEntity> getRepairsByPlate(@PathVariable String plate){
        return repairService.getRepairsByPlate(plate);
    }

    /* POST OPERATIONS */

    /*--------------------------------------------------------------------------------------------------------
     * createRepair: method to create a repair in the DB;
     *
     * @param plate - the registration plate of the vehicle;
     * @param repair - the repair to be saved in the DB;
     * @return - null;
     --------------------------------------------------------------------------------------------------------*/

    @PostMapping("/{plate}")
    public void createRepair(@PathVariable String plate, @RequestBody RepairEntity repair) {
        repairService.createRepair(plate, repair);
    }

    /* UPDATE OPERATIONS */

    /*--------------------------------------------------------------------------------------------------------
     * updateRepair: method to update a repair in the DB;
     *
     * @param updatedRepair - the updated repair;
     --------------------------------------------------------------------------------------------------------*/

    @PutMapping("/")
    public void updateRepair(@RequestBody RepairEntity updatedRepair) {
        repairService.updateRepair(updatedRepair);
    }

    /* DELETE OPERATIONS */

    /*--------------------------------------------------------------------------------------------------------
     * deleteRepair: method to delete a repair in the DB;
     *
     * @param id - the id of the repair to delete;
     * @return - null
     --------------------------------------------------------------------------------------------------------*/

    @DeleteMapping("/{id}")
    public void deleteRepair(@PathVariable Long id) {
        repairService.deleteRepair(id);
    }


    /* Business layer methods */

    /* SURCHARGE APPLICATION */

    /*--------------------------------------------------------------------------------------------------------
     * surchargeTotal: method to calculate the total surcharge for a repair;
     *
     * @param repair - the repair entity for which the surcharge is being calculated;
     * @return - the total surcharge amount for the repair;
     --------------------------------------------------------------------------------------------------------*/
    @GetMapping("/surchargeTotal/")
    public double surchargeTotal(@RequestBody RepairEntity repair){
        return repairService.surchargeTotal(repair);
    }

    /* DISCOUNT APPLICATION */


    /*--------------------------------------------------------------------------------------------------------
     * innerDiscount: method to calculate the total internal discount for a repair;
     *                (assuming autofix internal discounts only)
     *
     * @param repair - the repair entity for which the internal discount is being calculated;
     * @return - the total internal discount amount for the repair;
     --------------------------------------------------------------------------------------------------------*/
    @GetMapping("/indiscount/")
    public double innerDiscount(@RequestBody RepairEntity repair){
        return repairService.innerDiscount(repair);
    }


    /* REPORTS */

    @GetMapping("/R1/{plate}")
    public List<Double> R1(@PathVariable String plate){
        return repairService.calculationValues(plate);
    }

}
