package com.capstonebackend.managementuserservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "permissionrole")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PermissionRoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long roleid;

    private Long permissionid;
}
