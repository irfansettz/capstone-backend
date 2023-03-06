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
import java.util.Date;

@Entity
@Data
@RequiredArgsConstructor
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotNull
    private String uuid;

    @Column(unique = true)
    @NotNull
    private String username;

    @Column(unique = true)
    @NotNull
    private String email;

    @Column(unique = true)
    @NotNull
    private String password;

    @NotNull
    private boolean active;

    private String token;

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

    @Column(name = "date_non_active")
    private Date datenonactive;
}
