package com.aegis.homolog.orchestrator.entities;

import jakarta.persistence.*;

@Entity
@Table
public class Domain {

    @Column(name = "domainId", unique = true)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @Column
    private String createdAt;

    @Column
    private String updatedAt;

    @Column
    private String createdBy;


}
