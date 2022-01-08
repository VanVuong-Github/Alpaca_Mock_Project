package com.devcamp.Project.entity;

import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table( name = "claim_request")
@SQLDelete(sql = "UPDATE claim_request SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class ClaimRequest implements Serializable {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "please input name of customer")
    private String customerName;

    @NotNull(message = "please input cardId of customer")
    private String customerCardId;

    private boolean deleted = Boolean.FALSE;

}
