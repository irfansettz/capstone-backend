package com.capstonebackend.managementuserservice.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "departmentuser")
@Data
@RequiredArgsConstructor
public class UserDepartmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userid;
    private Long departmentid;

    public UserDepartmentEntity(long userid, Long departmentid) {
        this.userid = userid;
        this.departmentid = departmentid;
    }
}
