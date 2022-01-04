package com.devcamp.Project.entity;

import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "permission")
@Getter
@Setter
public class Permission extends BaseEntity {
    private String permissionName;

    private String permissionKey;
}
