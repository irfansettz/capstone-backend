package com.capstonebackend.managementuserservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "roleuser")
@Data
@RequiredArgsConstructor
public class UserRoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userid;
    private Long roleid;

    public UserRoleEntity(long userid, Long roleid) {
        this.userid = userid;
        this.roleid = roleid;
    }
}
