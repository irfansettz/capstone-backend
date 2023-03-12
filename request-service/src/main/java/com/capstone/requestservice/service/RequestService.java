package com.capstone.requestservice.service;

import com.capstone.requestservice.entity.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public interface RequestService {
    String addRequest(RequestEntity saveRequest);

    void updateByUiid(String uuid, String desc, String username);

    RequestEntity getByUuid(String requestUuid);

    List<RequestEntity> getAllRequest();

    void deleteByUuid(String uuid);
}
