package com.ikon.authservice.dto;

import com.ikon.authservice.util.UserDepartment;
import com.ikon.authservice.util.UserRole;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
public class UserDTO {
    private Integer id;
    private String username;
    private String email;
//    private String password;
    private String name;
    private UserRole role;
    private UserDepartment department;
}

