package com.capstone.requestservice.service;

import com.capstone.requestservice.dto.UpdateDetailDTO;
import com.capstone.requestservice.entity.RequestDetailEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RequestDetailService {
    void addDetail(RequestDetailEntity requestSave);

    void updateByUuid(String uuid, String username, UpdateDetailDTO detail);

    void deleteByUuid(String uuid);

    RequestDetailEntity getByUuid(String uuid);

    void saveAllDetail(List<RequestDetailEntity> requestDetails);
}
