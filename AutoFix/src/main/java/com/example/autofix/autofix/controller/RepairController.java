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
}
