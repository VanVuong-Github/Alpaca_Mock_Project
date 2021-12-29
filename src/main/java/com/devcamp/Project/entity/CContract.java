package com.devcamp.Project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table( name = "contract")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CContract {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "valid_from")
    private Date validFrom;
    @Column(name = "valid_to")
    private Date validTo;
    @Column(name = "maximum_amout")
    private double maximumAmount;
    @Column(name = "remaining_amout")
    private double remainingAmount;
    @Column(name = "acceptable_hospitals")
    private String acceptableHospitals;
    @Column(name = "acceptable_accidents")
    private String acceptableAccidents;

    @ManyToOne
    @JsonIgnore
    @JoinColumn( name = "customer_id")
    private CCustomer customer;
}
