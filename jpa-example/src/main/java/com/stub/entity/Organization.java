package com.stub.entity;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "t_organization")
public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String orgId;
    @OneToOne(targetEntity = OrganizationAddress.class, cascade = CascadeType.ALL)
    private OrganizationAddress organaizationAddress;

}