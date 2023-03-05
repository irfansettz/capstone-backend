package com.capstonebackend.managementuserservice.entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@RequiredArgsConstructor
@Table(name = "departments")
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotNull
    private String uuid;

    @Column(unique = true)
    @NotNull
    private String code;

    @Column(unique = true)
    @NotNull
    private String name;

    @NotNull
    private boolean active;

    @NotNull
    @Column(name = "created_by")
    private String createdby;

    @CreationTimestamp
    @Column(name = "created_on")
    private java.sql.Timestamp createdon;

    @NotNull
    @Column(name = "last_updated_by")
    private String lastupatedby;

    @UpdateTimestamp
    @Column(name = "last_updated_on")
    private Timestamp lastupdatedon;
}
