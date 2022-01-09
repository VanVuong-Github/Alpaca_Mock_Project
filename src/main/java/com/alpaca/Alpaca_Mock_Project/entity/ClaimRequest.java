package com.alpaca.Alpaca_Mock_Project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@TypeDefs({
        @TypeDef(
                name = "string-array",
                typeClass = StringArrayType.class
        )
})
@SQLDelete(sql = "UPDATE claim_request SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class ClaimRequest implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerName;
    private String cardId;

    @Type(type = "string-array")
    @Column(columnDefinition = "text[]")
    private String[] urls;

    @JsonIgnore
    private boolean deleted = Boolean.FALSE;
}
