package com.capstonebackend.managementuserservice.service.Impl;

import com.capstonebackend.managementuserservice.entity.UserRoleEntity;
import com.capstonebackend.managementuserservice.repository.UserRoleRepository;
import com.capstonebackend.managementuserservice.service.UserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserRoleServiceImpl implements UserRoleService {
    private final UserRoleRepository userRoleRepository;
    @Override
    public List<UserRoleEntity> getAllUserRolesByUserid(Long id) {
        return userRoleRepository.findAllByUserid(id);
    }

    @Override
    public void addData(UserRoleEntity userRole) {
        userRoleRepository.save(userRole);
    }

    @Override
    public UserRoleEntity getDataByUseridAndRoleid(long userid, Long roleid) {
        return userRoleRepository.findByUseridAndRoleid(userid, roleid);
    }

    @Override
    public void deleteById(Long id) {
        userRoleRepository.deleteById(id);
    }
}
