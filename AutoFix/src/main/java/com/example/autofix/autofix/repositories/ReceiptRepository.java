package com.example.autofix.autofix.repositories;

import com.example.autofix.autofix.entities.ReceiptEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceiptRepository extends JpaRepository<ReceiptEntity, Long> {
}
