package com.firstapi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class Role {
    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    private Integer rId;
    private String rName;

}
