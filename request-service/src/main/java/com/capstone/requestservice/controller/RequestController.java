package com.capstone.requestservice.controller;

import com.capstone.requestservice.dto.*;
import com.capstone.requestservice.entity.RequestDetailEntity;
import com.capstone.requestservice.entity.RequestEntity;
import com.capstone.requestservice.entity.RequestTypeEntity;
import com.capstone.requestservice.service.RequestService;
import com.capstone.requestservice.service.RequestTypeService;
import com.capstone.requestservice.service.impl.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
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
    private final RestTemplate restTemplate;


    private final EmailService emailService;


    @PostMapping
    public ResponseEntity<RequestUuidDTO> saveRequest(
            @RequestBody SaveRequestDTO request, @RequestHeader("Authorization") String authorizationHeader){
        String newToken = authorizationHeader.split(" ")[1];
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(newToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<UserInfoDTO> userInfo = restTemplate.exchange("http://localhost:8081/api/v1/auth/user-data/username-dept", HttpMethod.GET, entity, UserInfoDTO.class);
        ResponseEntity<DepartmentInfoDTO> deptInfo = restTemplate.exchange("http://localhost:8082/v1/api/users/departments/uuid/" + userInfo.getBody().getDepartmentuuid(), HttpMethod.GET, entity, DepartmentInfoDTO.class);
        RequestTypeEntity requestType = requestTypeService.getRequestTypeByUuid(request.getTypeUuid());
        RequestEntity saveRequest = new RequestEntity(null, UUID.randomUUID().toString(), requestType.getId(), deptInfo.getBody().getId(), generateNoTrans(deptInfo.getBody()), null, request.getDescription(), null, null, userInfo.getBody().getUsername(), null, userInfo.getBody().getUsername(), null, null);
        String uuid = requestService.addRequest(saveRequest);

        String messageText = "";
        messageText += "\n\nPermintaan anda dengan kode " + uuid;
        messageText += "\nsudah kami terima, terima kasih.";

        // Send email notification
        emailService.sendEmailMessage(
                "numanarif87@gmail.com",
                "Request for item",
                messageText
        );


        RequestUuidDTO response = new RequestUuidDTO(uuid);
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
        ResponseEntity<UserInfoDTO> userInfo = restTemplate.exchange("http://auth-service:8081/api/v1/auth/user-data/username-dept", HttpMethod.GET, entity, UserInfoDTO.class);
        requestService.updateByUiid(uuid, requestDTO.getDescription(), userInfo.getBody().getUsername());

        RequestUuidDTO response = new RequestUuidDTO(uuid);
        response.setCode(201);
        response.setStatus("success");
        response.setMessage("request updated");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<RequestDTO> getRequestByUuid(@PathVariable String uuid){
        RequestEntity request = requestService.getByUuid(uuid);
        RequestDTO requestDTO = getRequestResponse(request);
        return new ResponseEntity<>(requestDTO, HttpStatus.OK);
    }

    @GetMapping
    @Transactional
    public ResponseEntity<List<RequestDTO>> getAllRequest(){
        List<RequestEntity> resquests = requestService.getAllRequest();
        List<RequestDTO> requestDTOS = new ArrayList<>();

        for (RequestEntity request: resquests) {
            requestDTOS.add(getRequestResponse(request));
        }
        return new ResponseEntity<>(requestDTOS, HttpStatus.OK);
    }

    @DeleteMapping("/{uuid}")
    @Transactional
    public ResponseEntity<ResponseDTO> deleteRequestByUuid(@PathVariable String uuid){
        requestService.deleteByUuid(uuid);

        return new ResponseEntity<>(new ResponseDTO(201, "success", "Request deleted"), HttpStatus.OK);
    }
    public String generateNoTrans(DepartmentInfoDTO deptInfo){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMms");
        LocalDateTime dateTime = LocalDateTime.now();
        return "R" + String.format("00%d", deptInfo.getId()) + deptInfo.getCode() + dateTime.format(formatter);
    }

    public RequestDTO getRequestResponse(RequestEntity request){
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // get dept info
        ResponseEntity<DepartmentInfoDTO> deptInfo = restTemplate.exchange("http://management-user-service:8082/v1/api/users/departments/" + request.getDepartmentid(), HttpMethod.GET, entity, DepartmentInfoDTO.class);
        List<RequestDetailResponseDTO> details = new ArrayList<>();

        // get type
        RequestTypeEntity type = requestTypeService.getTypeById(request.getRequesttypeid());
        for (RequestDetailEntity detail: request.getDetails()) {
            ServiceDTO serviceDTO = null;
            ItemDTO itemDTO = null;
            // get item
            if (detail.getItemid() != null){

                ResponseEntity<ItemEntityDTO> response = restTemplate.exchange("http://item-service:8083/api/v1/items/" + detail.getItemid(), HttpMethod.GET, entity, ItemEntityDTO.class);
                ItemEntityDTO item = response.getBody();
                itemDTO = new ItemDTO(item.getUuid(), item.getName());
            }
            // get service
            if (detail.getServiceid() != null){
                ResponseEntity<ServiceEntityDTO> response = restTemplate.exchange("http://item-service:8083/api/v1/services/" + detail.getItemid(), HttpMethod.GET, entity, ServiceEntityDTO.class);
                ServiceEntityDTO service = response.getBody();
                serviceDTO = new ServiceDTO(service.getUuid(), service.getName());
            }
            details.add(new RequestDetailResponseDTO(detail.getUuid(), itemDTO, serviceDTO, detail.getQty(), detail.getPrice(), detail.getDescription(), detail.getCreatedby(),  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(detail.getCreatedon()), detail.getLastupdatedby(),  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(detail.getLastupdatedon())));
        }
        return new RequestDTO(request.getUuid(), new TypeDTO(type.getUuid(), type.getType()), deptInfo.getBody(), request.getNotrans(), request.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), request.getDescription(), request.getApprovaldpt(), request.getApprovalfnc(), request.getCreatedby(), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(request.getCreatedon()), request.getLastupdatedby(), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(request.getLastupdatedon()), details);
    }
}
