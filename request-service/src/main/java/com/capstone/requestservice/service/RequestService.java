package com.capstone.requestservice.service;

import com.capstone.requestservice.entity.RequestEntity;
import com.capstone.requestservice.entity.RequestTypeEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RequestService {
    List<RequestTypeEntity> getAllRequestType();

    RequestTypeEntity getRequestTypeByUuid(String uuid);

    String addRequest(RequestEntity saveRequest);


    void updateByUiid(String uuid, String desc, String username);
}
