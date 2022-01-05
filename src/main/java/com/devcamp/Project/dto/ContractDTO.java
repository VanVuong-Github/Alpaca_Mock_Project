package com.devcamp.Project.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.sql.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@RequiredArgsConstructor
@AllArgsConstructor
public class ContractDTO {

    private Date validFrom;
    private Date validTo;
    private double maximumAmount;
    private double remainingAmount;
    private String acceptableHospitals;
    private String acceptableAccidents;
}
