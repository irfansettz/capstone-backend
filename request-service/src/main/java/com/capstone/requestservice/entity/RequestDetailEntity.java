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
@Table(name = "requestdetail")
@AllArgsConstructor
@NoArgsConstructor
public class RequestDetailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String uuid;

    private Long requestid;

    private Long itemid;

    private Long serviceid;

    private Integer qty;

    private double price;

    private String description;

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
