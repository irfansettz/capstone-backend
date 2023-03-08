package com.capstonebackend.managementuserservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "approvalgroup")
@AllArgsConstructor
@NoArgsConstructor
public class ApprovalGroupEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long roleid;

    private Long moduleid;
}
