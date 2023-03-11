package com.capstone.requestservice.service;

import com.capstone.requestservice.entity.RequestTypeEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RequestTypeService {
    List<RequestTypeEntity> getAllRequestType();

    RequestTypeEntity getRequestTypeByUuid(String uuid);

    RequestTypeEntity getTypeById(Long requesttypeid);
}
