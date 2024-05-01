package com.example.autofix.autofix.repositories;

import com.example.autofix.autofix.entities.ReceiptEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReceiptRepository extends JpaRepository<ReceiptEntity, Long> {

    public ReceiptEntity findByVehiclePlate(String plate);

    public List<ReceiptEntity> findAllByVehiclePlate(String plate);
}
