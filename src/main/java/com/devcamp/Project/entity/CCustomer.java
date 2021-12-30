package com.devcamp.Project.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

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

    @Column( name = "card_id")
    private String cardId;
    private String phone;
    private String email;

    @Column( name = "date_of_bird")
    private Date dateOfBirth;
    private String address;
    private String occupation;

    @OneToMany( targetEntity = CContract.class,
                cascade = CascadeType.ALL,
                mappedBy = "customer")
    private List<CContract> contract;

//    @OneToMany( targetEntity = CClaimRequest.class,
//                cascade = CascadeType.ALL,
//                mappedBy = "customer")
//    private List<CClaimRequest> claimRequests;
	
	
	
}
