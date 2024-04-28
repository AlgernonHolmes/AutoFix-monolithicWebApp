package com.example.autofix.autofix.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;


@Entity
@Table(name = "repairs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RepairEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate entryVDate;
    private LocalTime entryVTime;
    private String repairType;
    private double totalCost;
    private double couponAssigned;
    /* I refer to
    *  exitVDate: vehicle exit date;
    *  exitCDate: customer exit date; */
    private LocalDate exitVDate;
    private LocalDate exitCDate;
    /* I refer to
    * exitVTime: vehicle exit time;
    * exitCTime: customer exit time; */
    private LocalTime exitVTime;
    private LocalTime exitCTime;
    /* next id will be the id
       of the corresponding vehicle */
    private String vehiclePlate;
}
