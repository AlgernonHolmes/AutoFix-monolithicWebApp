package com.example.autofix.autofix.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;


@Entity
@Table(name = "receipts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReceiptEntity {

    /* ATRIBUTES */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;
    private double totalPayment;
    private double totalSurcharge;
    private double totalDiscount;
    private double couponAssigned;
    /* to be understood:
      real payment = totalPayment + totalSurcharge - totalDiscount;
     */
    private double realPayment;
    private String vehiclePlate;
    private LocalDate receiptDate;
    private LocalTime receiptTime;


}
