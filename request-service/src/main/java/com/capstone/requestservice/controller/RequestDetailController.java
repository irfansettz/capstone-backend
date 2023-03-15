package com.capstone.requestservice.controller;

import com.capstone.requestservice.dto.*;
import com.capstone.requestservice.entity.RequestDetailEntity;
import com.capstone.requestservice.entity.RequestEntity;
import com.capstone.requestservice.entity.RequestTypeEntity;
import com.capstone.requestservice.service.RequestDetailService;
import com.capstone.requestservice.service.RequestService;
import com.capstone.requestservice.service.RequestTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
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
    @Transactional
    // todo add request header
    public ResponseEntity<ResponseDTO> addRequestDetail(@RequestBody RequestDetailDTO requestDetail){
        // get data request header
        RequestEntity request = requestService.getByUuid(requestDetail.getRequestUuid());
        // get data request type
        RequestTypeEntity requestType = requestTypeService.getTypeById(request.getRequesttypeid());
        RequestDetailEntity requestSave;
        if ( requestType.getType().equals("Item")){
            HttpHeaders headers = new HttpHeaders();

            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<ItemEntityDTO> response = restTemplate.exchange("http://localhost:8083/api/v1/items/uuid/" + requestDetail.getItemUuid(), HttpMethod.GET, entity, ItemEntityDTO.class);
            ItemEntityDTO item = response.getBody();
            requestSave = new RequestDetailEntity(null, UUID.randomUUID().toString(), request,item.getId(), null, requestDetail.getQty(), requestDetail.getPrice(), requestDetail.getDesc(), request.getCreatedby(), null, request.getCreatedby(), null);
        } else {
            HttpHeaders headers = new HttpHeaders();

            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<ServiceEntityDTO> response = restTemplate.exchange("http://localhost:8083/api/v1/services/uuid/" + requestDetail.getServiceUuid(), HttpMethod.GET, entity, ServiceEntityDTO.class);
            ServiceEntityDTO service = response.getBody();
            requestSave = new RequestDetailEntity(null, UUID.randomUUID().toString(), request,null, service.getId(), requestDetail.getQty(), requestDetail.getPrice(), requestDetail.getDesc(), request.getCreatedby(), null, request.getCreatedby(), null);
        }
        requestDetailService.addDetail(requestSave);
        return new ResponseEntity<>(new ResponseDTO(201, "success", "Request Detail Saved"), HttpStatus.CREATED);
    }

    @PutMapping("/{uuid}")
    @Transactional
    public ResponseEntity<ResponseDTO> updateDetailByUuid(@PathVariable String uuid, @RequestHeader("Authorization") String authorizationHeader, @RequestBody UpdateDetailDTO detail){
        // get info user
        String newToken = authorizationHeader.split(" ")[1];
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(newToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<UserInfoDTO> userInfo = restTemplate.exchange("http://localhost:8081/api/v1/auth/user-data/username-dept", HttpMethod.GET, entity, UserInfoDTO.class);

        requestDetailService.updateByUuid(uuid, userInfo.getBody().getUsername(), detail);
        return new ResponseEntity<>(new ResponseDTO(201, "success", "Detail updated"), HttpStatus.CREATED);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<ResponseDTO> deleteDetailByUuid(@PathVariable String uuid){
        requestDetailService.deleteByUuid(uuid);

        return new ResponseEntity<>(new ResponseDTO(201, "success", "Detail deleted"), HttpStatus.OK);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<RequestDetailResponseDTO> getRequestDetailBy(@PathVariable String uuid){
        RequestDetailEntity request = requestDetailService.getByUuid(uuid);
        ServiceDTO serviceDTO = null;
        ItemDTO itemDTO = null;
        // get item
        if (request.getItemid() != null){
            HttpHeaders headers = new HttpHeaders();

            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<ItemEntityDTO> response = restTemplate.exchange("http://localhost:8083/api/v1/items/" + request.getItemid(), HttpMethod.GET, entity, ItemEntityDTO.class);
            ItemEntityDTO item = response.getBody();
            itemDTO = new ItemDTO(item.getUuid(), item.getName());
        }
        // get service
        if (request.getServiceid() != null){
            HttpHeaders headers = new HttpHeaders();

            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<ServiceEntityDTO> response = restTemplate.exchange("http://localhost:8083/api/v1/services/" + request.getItemid(), HttpMethod.GET, entity, ServiceEntityDTO.class);
            ServiceEntityDTO service = response.getBody();
            serviceDTO = new ServiceDTO(service.getUuid(), service.getName());
        }
        RequestDetailResponseDTO responseDTO = new RequestDetailResponseDTO(request.getUuid(), itemDTO, serviceDTO, request.getQty(), request.getPrice(), request.getDescription(), request.getCreatedby(),  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(request.getCreatedon()), request.getLastupdatedby(),  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(request.getLastupdatedon()));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
