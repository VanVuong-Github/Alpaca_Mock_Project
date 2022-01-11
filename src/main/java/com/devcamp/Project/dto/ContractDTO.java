package com.devcamp.Project.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.sql.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@RequiredArgsConstructor
@Getter
@Setter
public class ContractDTO {

    private Date validFrom;
    private Date validTo;
    private double maximumAmount;
    private double remainingAmount;
    private String acceptableHospitals;
    private String acceptableAccidents;
    private String numContract;
}
