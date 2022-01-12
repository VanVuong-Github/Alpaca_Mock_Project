package com.devcamp.Project.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table( name = "customers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "customer", createIndex = true)
@SQLDelete(sql = "UPDATE customers SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class Customer implements Serializable {

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY)
	//@GeneratedValue(generator="system-uuid")
	//@GenericGenerator(name="system-uuid", strategy = "uuid")
	private Long id;

    @NotNull( message = "Please enter your name!")
    private String name;
    @NotNull( message = "Please enter your gender!")
    private String gender;

    @Column( name = "card_id", unique = true)
    @NotNull( message = "Please enter your cardId!")
    private Long cardId;

    @Column(unique = true)
    @NotNull( message = "Please enter your phone!")
    private String phone;

    @Column(unique = true)
    @NotNull( message = "Please enter your email!")
    private String email;

    @Column( name = "date_of_bird")
    @NotNull( message = "Please enter your date of birth!")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date dateOfBirth;
    @NotNull( message = "Please enter your address!")
    private String address;
    @NotNull( message = "Please enter your occupation!")
    private String occupation;


    private boolean deleted = Boolean.FALSE;

    @OneToMany( targetEntity = Contract.class,
                cascade = CascadeType.ALL,
                mappedBy = "customer")
    private List<Contract> contract;

}
