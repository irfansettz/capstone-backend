package com.capstone.requestservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = "requesttype")
@AllArgsConstructor
@NoArgsConstructor
public class RequestTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String uuid;

    private String type;

    @Column(name = "created_by")
    private String createdby;

    @Column(name = "created_on")
    @CreationTimestamp
    private Timestamp createdon;

    @Column(name = "last_updated_by")
    private String lastupdatedby;

    @UpdateTimestamp
    @Column(name = "last_updated_on")
    private Timestamp lastupdatedon;
}
