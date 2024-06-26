package com.example.autofix.autofix.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "vehicles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleEntity {

    /* ATRIBUTES */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;
    private String registrationPlate;
    private String brand;
    private String model;
    private String type;
    private int fabricationYear;
    private String engineType;
    private int numSeats;
    private int milage;
    /* next attribute stores the id of every repair
       related to a specific vehicle */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ElementCollection(fetch = FetchType.EAGER)
    private List<Long> idRepair;

}
