package com.capstone.requestservice.controller;

import com.capstone.requestservice.dto.*;
import com.capstone.requestservice.entity.RequestEntity;
import com.capstone.requestservice.entity.RequestTypeEntity;
import com.capstone.requestservice.service.RequestService;
import com.capstone.requestservice.service.RequestTypeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/api/requests")
@RequiredArgsConstructor
public class RequestController {
    private final RequestService requestService;
    private final RequestTypeService requestTypeService;
    private final ModelMapper modelMapper;
    private final RestTemplate restTemplate;

    @PostMapping
    public ResponseEntity<RequestResponseDTO> saveRequest(@RequestBody SaveRequestDTO request, @RequestHeader("Authorization") String authorizationHeader){
        String newToken = authorizationHeader.split(" ")[1];
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(newToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<UserInfoDTO> userInfo = restTemplate.exchange("http://localhost:8081/api/v1/auth/user-data/username-dept", HttpMethod.GET, entity, UserInfoDTO.class);
        ResponseEntity<DepartmentInfoDTO> deptInfo = restTemplate.exchange("http://localhost:8082/v1/api/users/departments/" + userInfo.getBody().getDepartmentuuid(), HttpMethod.GET, entity, DepartmentInfoDTO.class);
        RequestTypeEntity requestType = requestTypeService.getRequestTypeByUuid(request.getTypeUuid());
        RequestEntity saveRequest = new RequestEntity(null, UUID.randomUUID().toString(), requestType.getId(), deptInfo.getBody().getId(), generateNoTrans(deptInfo.getBody()), null, request.getDescription(), null, null, userInfo.getBody().getUsername(), null, userInfo.getBody().getUsername(), null);
        String uuid = requestService.addRequest(saveRequest);

        RequestResponseDTO response = new RequestResponseDTO(uuid);
        response.setCode(201);
        response.setStatus("success");
        response.setMessage("request saved");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<ResponseDTO> updateRequestByUuid(@RequestHeader("Authorization") String authorizationHeader, @RequestBody SaveRequestDTO requestDTO, @PathVariable String uuid){
        String newToken = authorizationHeader.split(" ")[1];
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(newToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<UserInfoDTO> userInfo = restTemplate.exchange("http://localhost:8081/api/v1/auth/user-data/username-dept", HttpMethod.GET, entity, UserInfoDTO.class);
        requestService.updateByUiid(uuid, requestDTO.getDescription(), userInfo.getBody().getUsername());

        RequestResponseDTO response = new RequestResponseDTO(uuid);
        response.setCode(201);
        response.setStatus("success");
        response.setMessage("request updated");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public String generateNoTrans(DepartmentInfoDTO deptInfo){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMms");
        LocalDateTime dateTime = LocalDateTime.now();
        return "R" + String.format("00%d", deptInfo.getId()) + deptInfo.getCode() + dateTime.format(formatter);
    }
}
