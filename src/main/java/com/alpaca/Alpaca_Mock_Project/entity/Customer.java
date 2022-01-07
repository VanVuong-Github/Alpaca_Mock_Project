package com.alpaca.Alpaca_Mock_Project.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Document(indexName = "customer", createIndex = true)
public class Customer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Field(type = FieldType.Text)
    private String name;
    private String gender;

    @Field(type = FieldType.Text)
    private String cardId;
    private String phone;
    private String email;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date dateOfBirth;
    private String address;
    private String occupation;
}
