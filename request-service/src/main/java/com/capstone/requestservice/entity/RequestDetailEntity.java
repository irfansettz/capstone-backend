package com.capstone.requestservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.transaction.annotation.Transactional;

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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "requestid")
    private RequestEntity requestid;

    private Long itemid;

    private Long serviceid;

    private Integer qty;

    private double price;

    @Column(name = "description", length = 500)
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
