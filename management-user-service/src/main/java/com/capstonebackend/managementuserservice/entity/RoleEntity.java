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
@Table(name = "roles")
@AllArgsConstructor
@NoArgsConstructor
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(unique = true)
    private String uuid;

    @NotNull
    @Column(unique = true)
    private String name;

    @Column(name = "created_by")
    @NotNull
    private String createdby;

    @CreationTimestamp
    @Column(name = "created_on")
    private java.sql.Timestamp createdon;

    @Column(name = "last_updated_by")
    @NotNull
    private String lastupatedby;

    @UpdateTimestamp
    @Column(name = "last_updated_on")
    private Timestamp lastupdatedon;
}
