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

    /* COUPON APPLICATION */

    @PostMapping("/coupon/{idRepair}")
    public List<List<Double>> applyCoupon(@PathVariable Long idRepair, @RequestBody List<List<Double>> values){
        return repairService.applyCoupon(idRepair, values);
    }


    /* REPORTS */

    /* R1 */
    /*--------------------------------------------------------------------------------------------------------
     * calculationReport: Method to generate a calculation report based on vehicle repair history.
     *
     * @return - A list containing calculation reports for each vehicle.
     --------------------------------------------------------------------------------------------------------*/
    @GetMapping("/R1/")
    public List<List<Double>> R1(){
        return repairService.calculationReport();
    }


    /* R2 */
    /*--------------------------------------------------------------------------------------------------------
     * repairTypeReport: method to generate a report of repair costs categorized by repair type and vehicle type.
     *
     * @return - a list of lists containing the total repair costs for each combination of repair type and
     *           vehicle type.
     --------------------------------------------------------------------------------------------------------*/
    @GetMapping("/R2/")
    public List<List<Double>> R2(){
        return repairService.repairTypeReport();
    }

    /* R3 */
    /*--------------------------------------------------------------------------------------------------------
     * repairTimeReport: method to generate a report of average repair times for each vehicle brand;
     *
     *
     * @return - a list containing the average repair time for each vehicle brand;
     --------------------------------------------------------------------------------------------------------*/
    @GetMapping("/R3/")
    public List<Double> R3(){
        return repairService.repairTimeReport();
    }

    /* R4 */
    /*--------------------------------------------------------------------------------------------------------
     * motorRepairReport: method to generate a report of repair statistics based on vehicle engine types and repair types;
     *
     * @return - a list of lists containing repair statistics for each vehicle engine and repair type;
     --------------------------------------------------------------------------------------------------------*/
    @GetMapping("/R4/")
    public List<List<Double>> R4(){
        return repairService.motorRepairReport();
    }
}
