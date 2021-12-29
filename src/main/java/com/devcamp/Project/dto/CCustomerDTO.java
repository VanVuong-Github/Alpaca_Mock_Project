package com.devcamp.Project.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.sql.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@RequiredArgsConstructor
@AllArgsConstructor
public class CCustomerDTO {
    private String name;
    private String gender;
    private String cardId;
    private String phone;
    private String email;
    private Date dateOfBirth;
    private String address;
    private String occupation;
}
