package com.devcamp.Project.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table( name = "customers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CCustomer {

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY)
	//@GeneratedValue(generator="system-uuid")
	//@GenericGenerator(name="system-uuid", strategy = "uuid")
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
