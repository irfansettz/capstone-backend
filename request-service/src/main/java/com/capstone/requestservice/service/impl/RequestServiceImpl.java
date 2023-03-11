package com.capstone.requestservice.service.impl;

import com.capstone.requestservice.entity.RequestEntity;
import com.capstone.requestservice.repository.ReqeustRepository;
import com.capstone.requestservice.service.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
        RequestEntity request = reqeustRepository.findByUuid(uuid);
        request.setDescription(desc);
        request.setLastupdatedby(username);
        reqeustRepository.save(request).getUuid();
    }

    @Override
    public RequestEntity getByUuid(String requestUuid) {
        return reqeustRepository.findByUuid(requestUuid);
    }
}
