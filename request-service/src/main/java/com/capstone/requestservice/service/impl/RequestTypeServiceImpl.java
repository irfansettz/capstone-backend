package com.capstone.requestservice.service.impl;

import com.capstone.requestservice.entity.RequestTypeEntity;
import com.capstone.requestservice.exception.RequestTypeNotFound;
import com.capstone.requestservice.repository.RequestTypeRepository;
import com.capstone.requestservice.service.RequestTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestTypeServiceImpl implements RequestTypeService {
    private final RequestTypeRepository requestTypeRepository;
    @Override
    public List<RequestTypeEntity> getAllRequestType() {
        return requestTypeRepository.findAll();
    }

    @Override
    public RequestTypeEntity getRequestTypeByUuid(String uuid) {
        if (requestTypeRepository.findByUuid(uuid) == null) throw new RequestTypeNotFound("Request Type Not Found");
        return requestTypeRepository.findByUuid(uuid);
    }

    @Override
    public RequestTypeEntity getTypeById(Long requesttypeid) {
        return requestTypeRepository.findById(requesttypeid).get();
    }
}
