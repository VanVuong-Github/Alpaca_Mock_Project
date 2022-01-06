package com.devcamp.Project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Entity
@Table( name = "contract")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE contract SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
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

    private boolean deleted = Boolean.FALSE;

    @ManyToOne
    @JsonIgnore
    @JoinColumn( name = "customer_id")
    private Customer customer;
}
