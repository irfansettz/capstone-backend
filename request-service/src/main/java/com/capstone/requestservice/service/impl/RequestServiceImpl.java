package com.capstone.requestservice.service.impl;

import com.capstone.requestservice.entity.RequestEntity;
import com.capstone.requestservice.exception.RequestNotFound;
import com.capstone.requestservice.repository.ReqeustRepository;
import com.capstone.requestservice.service.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {
    private final ReqeustRepository reqeustRepository;

    @Override
    public String addRequest(RequestEntity saveRequest) {
        return reqeustRepository.save(saveRequest).getUuid();
    }

    @Override
    public void updateByUiid(String uuid, String desc, String username) {
        if (reqeustRepository.findByUuid(uuid) == null) throw new RequestNotFound("Request Not Found");
        RequestEntity request = reqeustRepository.findByUuid(uuid);
        request.setDescription(desc);
        request.setLastupdatedby(username);
        reqeustRepository.save(request);
    }

    @Override
    public RequestEntity getByUuid(String requestUuid) {
        if (reqeustRepository.findByUuid(requestUuid) == null) throw new RequestNotFound("Request Not Found");
        return reqeustRepository.findByUuid(requestUuid);
    }

    @Override
    public List<RequestEntity> getAllRequest() {
        return reqeustRepository.findAll();
    }

    @Override
    public void deleteByUuid(String uuid) {
        if (reqeustRepository.findByUuid(uuid) == null) throw new RequestNotFound("Request Not Found");

        reqeustRepository.delete(reqeustRepository.findByUuid(uuid));
    }
}
