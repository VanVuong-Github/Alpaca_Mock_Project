package com.devcamp.Project.dto;

import com.devcamp.Project.entity.Contract;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.hibernate.annotations.Where;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@RequiredArgsConstructor
@Getter
@Setter
//@Where(clause = "deleted=false")
public class CustomerDTO implements Serializable {
    private String name;
    private String gender;
    private String cardId;
    private String phone;
    private String email;
    private Date dateOfBirth;
    private String address;
    private String occupation;
    private List<Contract> contract;


    public CustomerDTO(String name, String gender, String cardId, String phone, String email, Date dateOfBirth, String address, String occupation) {
        this.name = name;
        this.gender = gender;
        this.cardId = cardId;
        this.phone = phone;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.occupation = occupation;
    }
}
