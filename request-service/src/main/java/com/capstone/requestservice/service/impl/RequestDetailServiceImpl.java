package com.capstone.requestservice.service.impl;

import com.capstone.requestservice.dto.UpdateDetailDTO;
import com.capstone.requestservice.entity.RequestDetailEntity;
import com.capstone.requestservice.exception.RequestDetailNotFound;
import com.capstone.requestservice.repository.RequestDetailRepository;
import com.capstone.requestservice.service.RequestDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestDetailServiceImpl implements RequestDetailService {
    private final RequestDetailRepository requestDetailRepository;

    @Override
    public void addDetail(RequestDetailEntity requestSave) {
        requestDetailRepository.save(requestSave);
    }

    @Override
    public void updateByUuid(String uuid, String username, UpdateDetailDTO detail) {
        if (requestDetailRepository.findByUuid(uuid) == null ) throw new RequestDetailNotFound("Request detail not found");
        RequestDetailEntity requestDetail = requestDetailRepository.findByUuid(uuid);
        requestDetail.setLastupdatedby(username);
        requestDetail.setQty(detail.getQty());
        requestDetail.setPrice(detail.getPrice());
        requestDetail.setDescription(detail.getDesc());
        requestDetailRepository.save(requestDetail);
    }

    @Override
    public void deleteByUuid(String uuid) {
        if (requestDetailRepository.findByUuid(uuid) == null ) throw new RequestDetailNotFound("Request detail not found");
        RequestDetailEntity request = requestDetailRepository.findByUuid(uuid);
        requestDetailRepository.delete(request);
    }

    @Override
    public RequestDetailEntity getByUuid(String uuid) {
        if (requestDetailRepository.findByUuid(uuid) == null ) throw new RequestDetailNotFound("Request detail not found");
        return requestDetailRepository.findByUuid(uuid);
    }

    @Override
    public void saveAllDetail(List<RequestDetailEntity> requestDetails) {
        requestDetailRepository.saveAll(requestDetails);
    }
}
