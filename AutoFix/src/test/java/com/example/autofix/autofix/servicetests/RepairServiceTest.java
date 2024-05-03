package com.example.autofix.autofix.servicetests;

import com.example.autofix.autofix.entities.RepairEntity;
import com.example.autofix.autofix.entities.VehicleEntity;
import com.example.autofix.autofix.repositories.RepairRepository;
import com.example.autofix.autofix.repositories.VehicleRepository;
import com.example.autofix.autofix.services.RepairService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RepairServiceTest{

    @Mock
    private RepairRepository repairRepository;

    @Mock
    private VehicleRepository vehicleRepository;

    @InjectMocks
    private RepairService repairService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetRepairs() {
        // Mock data
        List<RepairEntity> repairs = new ArrayList<>();
        when(repairRepository.findAll()).thenReturn(repairs);

        // Test
        assertEquals(repairs, repairService.getRepairs());
    }

    @Test
    public void testGetRepairsByPlate() {
        // Mock data
        String plate = "ABC123";
        List<RepairEntity> repairs = new ArrayList<>();
        when(repairRepository.findAllByVehiclePlate(plate)).thenReturn(repairs);

        // Test
        assertEquals(repairs, repairService.getRepairsByPlate(plate));
    }

    @Test
    public void testAssignPrice() {
        // Test
        assertEquals(120000, repairService.assignPrice("frenos", "gasolina"));
    }

    @Test
    public void testCreateRepair() {
        // Mock data
        String plate = "ABC123";
        RepairEntity repair = new RepairEntity();
        repair.setRepairType("Parabrisas y Cristales");
        VehicleEntity vehicle = new VehicleEntity();
        vehicle.setRegistrationPlate("ABC123");
        vehicle.setBrand("Toyota");
        vehicle.setModel("Corolla");
        vehicle.setType("Sedan");
        vehicle.setFabricationYear(2022);
        vehicle.setEngineType("gasolina");
        vehicle.setNumSeats(5);
        vehicle.setMilage(0);
        List<Long> idRepair = new ArrayList<>();
        idRepair.add(1L);
        vehicle.setIdRepair(idRepair);

        when(vehicleRepository.findByRegistrationPlateWithRepairs(plate)).thenReturn(vehicle);
        when(repairRepository.save(repair)).thenReturn(repair);

        // Test
        repairService.createRepair(plate, repair);
        verify(repairRepository, times(1)).save(repair);
    }


    @Test
    public void testUpdateRepair() {
        // Mock data
        RepairEntity repair = new RepairEntity();

        // Test
        repairService.updateRepair(repair);
        verify(repairRepository, times(1)).save(repair);
    }

    @Test
    public void testDeleteRepair() {
        // Mock data
        Long id = 1L;

        // Test
        repairService.deleteRepair(id);
        verify(repairRepository, times(1)).deleteById(id);
    }


}
