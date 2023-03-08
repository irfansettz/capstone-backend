package com.capstonebackend.managementuserservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "permissions")
public class PermissionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String uuid;

    private String name;

    private String slug;

    private String nested;

    private boolean asmenu;

    @Column(name = "created_by")
    private String createdby;

    @CreationTimestamp
    @Column(name = "created_on")
    private Timestamp createdon;

    @Column(name = "last_updated_by")
    private String lastupdatedby;

    @UpdateTimestamp
    @Column(name = "last_updated_on")
    private Timestamp lastupdatedon;
}
