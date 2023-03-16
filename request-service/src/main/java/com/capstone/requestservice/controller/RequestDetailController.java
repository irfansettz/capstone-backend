package com.capstone.requestservice.controller;

import com.capstone.requestservice.dto.*;
import com.capstone.requestservice.entity.RequestDetailEntity;
import com.capstone.requestservice.service.RequestDetailService;
import com.capstone.requestservice.service.RequestService;
import com.capstone.requestservice.service.RequestTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;

@RestController
@RequestMapping("/v1/api/request-details")
@RequiredArgsConstructor
public class RequestDetailController {
    private final RequestDetailService requestDetailService;
    private final RestTemplate restTemplate;

    @PutMapping("/{uuid}")
    @Transactional
    public ResponseEntity<ResponseDTO> updateDetailByUuid(@PathVariable String uuid, @RequestHeader("Authorization") String authorizationHeader, @RequestBody UpdateDetailDTO detail){
        // get info user
        String newToken = authorizationHeader.split(" ")[1];
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(newToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<UserInfoDTO> userInfo = restTemplate.exchange("http://auth-service:8081/api/v1/auth/user-data/username-dept", HttpMethod.GET, entity, UserInfoDTO.class);

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
            ResponseEntity<ItemEntityDTO> response = restTemplate.exchange("http://item-service:8083/api/v1/items/" + request.getItemid(), HttpMethod.GET, entity, ItemEntityDTO.class);
            ItemEntityDTO item = response.getBody();
            itemDTO = new ItemDTO(item.getUuid(), item.getName());
        }
        // get service
        if (request.getServiceid() != null){
            HttpHeaders headers = new HttpHeaders();

            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<ServiceEntityDTO> response = restTemplate.exchange("http://item-service:8083/api/v1/services/" + request.getItemid(), HttpMethod.GET, entity, ServiceEntityDTO.class);
            ServiceEntityDTO service = response.getBody();
            serviceDTO = new ServiceDTO(service.getUuid(), service.getName());
        }
        RequestDetailResponseDTO responseDTO = new RequestDetailResponseDTO(request.getUuid(), itemDTO, serviceDTO, request.getQty(), request.getPrice(), request.getDescription(), request.getCreatedby(),  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(request.getCreatedon()), request.getLastupdatedby(),  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(request.getLastupdatedon()));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
