package com.example.autofix.autofix.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Entity
@Table(name = "repairs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RepairEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date entryVDate;
    private String repairType;
    private double totalCost;
    /* I refer to
    *  exitVDate: vehicle exit date;
    *  exitCDate: customer exit date; */
    private Date exitVDate;
    private Date exitCDate;
    /* next id will be the id
       of the corresponding vehicle */
    private Long vehicleId;
}
