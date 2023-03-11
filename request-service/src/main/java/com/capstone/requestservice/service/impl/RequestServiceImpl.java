package com.capstone.requestservice.service.impl;

import com.capstone.requestservice.entity.RequestEntity;
import com.capstone.requestservice.entity.RequestTypeEntity;
import com.capstone.requestservice.exception.RequestTypeNotFound;
import com.capstone.requestservice.repository.RequestTypeRepository;
import com.capstone.requestservice.repository.ReqeustRepository;
import com.capstone.requestservice.service.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {
    private final RequestTypeRepository requestTypeRepository;
    private final ReqeustRepository reqeustRepository;

    @Override
    public List<RequestTypeEntity> getAllRequestType() {
        return requestTypeRepository.findAll();
    }

    @Override
    public RequestTypeEntity getRequestTypeByUuid(String uuid) {
        if (requestTypeRepository.findByUuid(uuid) == null) throw new RequestTypeNotFound("failed", "Request Type Not Found");
        return requestTypeRepository.findByUuid(uuid);
    }

    @Override
    public String addRequest(RequestEntity saveRequest) {
        return reqeustRepository.save(saveRequest).getUuid();
    }

    @Override
    public void updateByUiid(String uuid, String desc, String username) {
        RequestEntity request = reqeustRepository.findByUuid(uuid);
        request.setDescription(desc);
        request.setLastupdatedby(username);
        reqeustRepository.save(request).getUuid();
    }
}
