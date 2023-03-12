package com.capstone.requestservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Table(name = "request")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String uuid;

    private Long requesttypeid;

    private Long departmentid;

    @Column(name = "no_trans")
    private String notrans;

    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "date", columnDefinition = "DATE")
    private LocalDateTime date;

    private String description;

    @Column(name = "approval_dpt")
    private Long approvaldpt;

    @Column(name = "approval_fnc")
    private Long approvalfnc;

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

    @OneToMany(mappedBy = "requestid", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RequestDetailEntity> details;
}
