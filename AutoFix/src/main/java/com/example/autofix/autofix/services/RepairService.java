package com.example.autofix.autofix.services;

import com.example.autofix.autofix.entities.RepairEntity;
import com.example.autofix.autofix.repositories.RepairRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class RepairService {

    /* Repository layer methods */

    @Autowired
    RepairRepository repairRepository;

    /*--------------------------------------------------------------------------------------------------------
     * getRepairs: method to find all repairs in the DB;
     *
     * @return - a list with all repairs;
     --------------------------------------------------------------------------------------------------------*/

    public ArrayList<RepairEntity> getRepairs(){
        return (ArrayList<RepairEntity>) repairRepository.findAll();
    }


}
