package com.devcamp.Project.entity;

import lombok.*;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "token")
@Getter
@Setter
public class Token extends BaseEntity{
    @Column(length = 1000)
    private String token;

    private Date tokenExpDate;
}
