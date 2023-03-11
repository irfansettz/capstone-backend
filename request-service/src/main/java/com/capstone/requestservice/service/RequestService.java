package com.capstone.requestservice.service;

import com.capstone.requestservice.entity.RequestEntity;
import com.capstone.requestservice.entity.RequestTypeEntity;
import org.springframework.stereotype.Service;


@Service
public interface RequestService {

    String addRequest(RequestEntity saveRequest);


    void updateByUiid(String uuid, String desc, String username);

    RequestEntity getByUuid(String requestUuid);
}
