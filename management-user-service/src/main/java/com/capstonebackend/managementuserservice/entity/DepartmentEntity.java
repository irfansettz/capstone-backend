package com.capstonebackend.managementuserservice.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@RequiredArgsConstructor
@Table(name = "departments")
public class DepartmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String uuid;

    private String code;

    private String name;

    private boolean active;

    @Column(name = "created_by")
    private String createdby;

    @CreationTimestamp
    @Column(name = "created_on")
    private java.sql.Timestamp createdon;

    @Column(name = "last_updated_by")
    private String lastupatedby;

    @UpdateTimestamp
    @Column(name = "last_updated_on")
    private Timestamp lastupdatedon;
}
