package com.devcamp.Project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Entity
@Table( name = "contract")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Contract {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "valid_from")
    private Date validFrom;
    @Column(name = "valid_to")
    private Date validTo;
    @Column(name = "maximum_amout")
    @NotNull(message = "please input maximum amount!")
    private double maximumAmount;

    @Column(name = "remaining_amout")
    @NotNull(message = "please input remaining amount!")
    private double remainingAmount;

    @Column(name = "acceptable_hospitals")
    @NotNull(message = "please input acceptable hospitals!")
    private String acceptableHospitals;

    @Column(name = "acceptable_accidents")
    @NotNull(message = "please input acceptable accidents!")
    private String acceptableAccidents;

    @ManyToOne
    @JsonIgnore
    @JoinColumn( name = "customer_id")
    @NotNull( message = "Please input Id of customer!")
    private Customer customer;
}
