package com.capstone.requestservice.service.impl;

import com.capstone.requestservice.entity.RequestDetailEntity;
import com.capstone.requestservice.repository.RequestDetailRepository;
import com.capstone.requestservice.service.RequestDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RequestDetailServiceImpl implements RequestDetailService {
    private final RequestDetailRepository requestDetailRepository;

    @Override
    public void addDetail(RequestDetailEntity requestSave) {
        requestDetailRepository.save(requestSave);
    }
}
