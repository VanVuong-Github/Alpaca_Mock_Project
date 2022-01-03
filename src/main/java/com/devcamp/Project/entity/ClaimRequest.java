package com.devcamp.Project.entity;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table( name = "claim_request")
public class ClaimRequest {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;
    private String customerName;
    private String customerCardId;

    @Lob
    private List<MultipartFile> files;

}
