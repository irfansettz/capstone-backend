package com.capstone.requestservice.service;

import com.capstone.requestservice.entity.RequestDetailEntity;
import org.springframework.stereotype.Service;

@Service
public interface RequestDetailService {
    void addDetail(RequestDetailEntity requestSave);
}
