package com.capstone.requestservice.controller;

import com.capstone.requestservice.dto.ItemEntityDTO;
import com.capstone.requestservice.dto.RequestDetailDTO;
import com.capstone.requestservice.dto.ResponseDTO;
import com.capstone.requestservice.dto.ServiceEntityDTO;
import com.capstone.requestservice.entity.RequestDetailEntity;
import com.capstone.requestservice.entity.RequestEntity;
import com.capstone.requestservice.entity.RequestTypeEntity;
import com.capstone.requestservice.service.RequestDetailService;
import com.capstone.requestservice.service.RequestService;
import com.capstone.requestservice.service.RequestTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@RestController
@RequestMapping("/v1/api/request-details")
@RequiredArgsConstructor
public class RequestDetailController {
    private final RequestDetailService requestDetailService;
    private final RequestService requestService;
    private final RequestTypeService requestTypeService;
    private final RestTemplate restTemplate;

    @PostMapping
    public ResponseEntity<ResponseDTO> addRequestDetail(@RequestBody RequestDetailDTO requestDetail){
        // get data request header
        RequestEntity request = requestService.getByUuid(requestDetail.getRequestUuid());
        // get data request type
        RequestTypeEntity requestType = requestTypeService.getTypeById(request.getRequesttypeid());
        RequestDetailEntity requestSave;
        if (requestType.getType() == "Item"){
            HttpHeaders headers = new HttpHeaders();

            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<ItemEntityDTO> response = restTemplate.exchange("http://localhost:8083/api/v1/items/uuid/" + requestDetail.getItemUuid(), HttpMethod.GET, entity, ItemEntityDTO.class);
            ItemEntityDTO item = response.getBody();
            System.out.println(item);

            requestSave = new RequestDetailEntity(null, UUID.randomUUID().toString(), request.getId(),item.getId(), null, requestDetail.getQty(), requestDetail.getPrice(), requestDetail.getDesc(), request.getCreatedby(), null, request.getCreatedby(), null);
        } else {
            HttpHeaders headers = new HttpHeaders();

            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<ServiceEntityDTO> response = restTemplate.exchange("http://localhost:8083/api/v1/services/uuid/" + requestDetail.getServiceUuid(), HttpMethod.GET, entity, ServiceEntityDTO.class);
            ServiceEntityDTO service = response.getBody();
            System.out.println(service);
            requestSave = new RequestDetailEntity(null, UUID.randomUUID().toString(), request.getId(),null, service.getId(), requestDetail.getQty(), requestDetail.getPrice(), requestDetail.getDesc(), request.getCreatedby(), null, request.getCreatedby(), null);
        }
        requestDetailService.addDetail(requestSave);
        return new ResponseEntity<>(new ResponseDTO(201, "success", "Request Detail Saved"), HttpStatus.CREATED);
    }

}
