package com.alpaca.Alpaca_Mock_Project.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerDto implements Serializable {
    @Id
    @JsonIgnore
    private Long id;
    private String name;
    private String gender;
    private String cardId;
    private String phone;
    private String email;
    private Date dateOfBirth;
    private String address;
    private String occupation;
}
