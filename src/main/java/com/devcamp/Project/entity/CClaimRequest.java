package com.devcamp.Project.entity;

import com.devcamp.Project.entity.CCustomer;
import com.devcamp.Project.entity.CFile;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table( name = "claim_request")
public class CClaimRequest {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;
    private String customerName;
    private String customerCardId;

    @Lob
    private List<MultipartFile> files;

}
