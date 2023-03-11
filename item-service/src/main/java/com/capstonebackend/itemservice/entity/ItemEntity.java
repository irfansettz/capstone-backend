package com.capstonebackend.itemservice.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.jetbrains.annotations.NotNull;

import java.sql.Timestamp;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotNull
    private String uuid;

    @Column(unique = true)
    @NotNull
    private String name;

    @Column(name = "created_by")
    @NotNull
    private String created_by;

    @Column(name = "created_on")
    @CreationTimestamp
    private Timestamp created_on;

    @NotNull
    @Column(name = "last_updated_by")
    private String lastupatedby;

    @UpdateTimestamp
    @Column(name = "last_updated_on")
    private Timestamp lastupdatedon;

}
