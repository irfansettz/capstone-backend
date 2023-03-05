package com.capstonebackend.managementuserservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Data
@RequiredArgsConstructor
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String uuid;

    private String username;

    private String email;

    private String password;

    private boolean active;

    private String token;

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

    @Column(name = "date_non_active")
    private Date datenonactive;
}
