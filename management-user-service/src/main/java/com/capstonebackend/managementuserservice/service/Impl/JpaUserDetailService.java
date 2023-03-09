package com.capstonebackend.managementuserservice.service.Impl;

import com.capstonebackend.managementuserservice.dto.UserDTO;
import com.capstonebackend.managementuserservice.entity.UserEntity;
import com.capstonebackend.managementuserservice.entity.UserSecurity;
import com.capstonebackend.managementuserservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class JpaUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username);
        if(user != null) {
            return new UserSecurity(user);
        }
        throw new UsernameNotFoundException("username not found");
    }

//    public List<UserDTO> getAll() {
//
//        List<User> users = userRepository.findAll();
//        List<UserDTO> userDTOList = new ArrayList<>();
//
//        for(User user : users) {
//            userDTOList.add(modelMapper.map(user, UserDTO.class));
//        }
//
//        return userDTOList;
//    }


}


