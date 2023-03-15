package com.capstone.approvalservice.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "approval")
public class ApprovalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String uuid;

    private Long departmentid;

    private Long moduleid;

    private String description;

    private String status;

    @Column(name = "crated_by")
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
