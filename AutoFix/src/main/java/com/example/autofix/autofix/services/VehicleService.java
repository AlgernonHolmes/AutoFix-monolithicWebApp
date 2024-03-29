package com.example.autofix.autofix.services;


import com.example.autofix.autofix.entities.VehicleEntity;
import com.example.autofix.autofix.repositories.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class VehicleService {

    /*  CRUD OPERATIONS */

    @Autowired
    VehicleRepository vehicleRepository;

    public ArrayList<VehicleEntity> getVehicles(){
        return (ArrayList<VehicleEntity>) vehicleRepository.findAll();
    }


}
