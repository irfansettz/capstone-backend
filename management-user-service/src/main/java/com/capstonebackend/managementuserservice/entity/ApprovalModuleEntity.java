package com.capstonebackend.managementuserservice.entity;

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
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "approvalmodule")
public class ApprovalModuleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String uuid;

    private String name;

    private String explain;

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
