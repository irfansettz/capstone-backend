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
    private final RequestDetailService requestDetailService;

    @PostMapping
    @Transactional
    public ResponseEntity<RequestUuidDTO> saveRequest(@RequestBody AddRequestDetailDTO request, @RequestHeader("Authorization") String authorizationHeader){
        String newToken = authorizationHeader.split(" ")[1];
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(newToken);

        // save header
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<UserInfoDTO> userInfo = restTemplate.exchange("http://localhost:8081/api/v1/auth/user-data/username-dept", HttpMethod.GET, entity, UserInfoDTO.class);
        ResponseEntity<DepartmentInfoDTO> deptInfo = restTemplate.exchange("http://localhost:8082/v1/api/users/departments/uuid/" + userInfo.getBody().getDepartmentuuid(), HttpMethod.GET, entity, DepartmentInfoDTO.class);
        RequestTypeEntity requestType = requestTypeService.getRequestTypeByUuid(request.getHeader().getTypeUuid());
        RequestEntity saveRequest = new RequestEntity(null, UUID.randomUUID().toString(), requestType.getId(), deptInfo.getBody().getId(), generateNoTrans(deptInfo.getBody()), null, request.getHeader().getDescription(), null, null, userInfo.getBody().getUsername(), null, userInfo.getBody().getUsername(), null, null);
        RequestEntity requestSaved = requestService.addRequest(saveRequest);

        // save detail
        List<RequestDetailEntity> requestDetails = new ArrayList<>();
        for (RequestDetailDTO detail: request.getDetails()) {
            if ( requestType.getType().equals("Item")){
                ResponseEntity<ItemEntityDTO> response = restTemplate.exchange("http://localhost:8083/api/v1/items/uuid/" + detail.getItemUuid(), HttpMethod.GET, entity, ItemEntityDTO.class);
                ItemEntityDTO item = response.getBody();
                requestDetails.add(new RequestDetailEntity(null, UUID.randomUUID().toString(), requestSaved,item.getId(), null, detail.getQty(), detail.getPrice(), detail.getDesc(), requestSaved.getCreatedby(), null, requestSaved.getCreatedby(), null));
            } else if( requestType.getType().equals("Service")) {
                ResponseEntity<ServiceEntityDTO> response = restTemplate.exchange("http://localhost:8083/api/v1/services/uuid/" + detail.getServiceUuid(), HttpMethod.GET, entity, ServiceEntityDTO.class);
                ServiceEntityDTO service = response.getBody();
                requestDetails.add(new RequestDetailEntity(null, UUID.randomUUID().toString(), requestSaved,null, service.getId(), detail.getQty(), detail.getPrice(), detail.getDesc(), requestSaved.getCreatedby(), null, requestSaved.getCreatedby(), null));
            }
        }
        requestDetailService.saveAllDetail(requestDetails);

        RequestUuidDTO response = new RequestUuidDTO(requestSaved.getUuid());
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
    public ResponseEntity<List<RequestDTO>> getAllRequest(@RequestParam(name = "departmentid", required = false) Long departmentid){
        List<RequestEntity> resquests = null;
        if (departmentid != null){
            resquests = requestService.getAllByDepartmentid(departmentid);
        } else {
            resquests = requestService.getAllRequest();
        }
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

    @PutMapping("approval/{uuid}")
    @Transactional
    public ResponseEntity<String> udpdateApproval(@RequestHeader("Authorization") String authorizationHeader, @RequestBody UpdateApprovalDTO approvalDTO, @PathVariable String uuid){
        String newToken = authorizationHeader.split(" ")[1];
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(newToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<UserInfoDTO> userInfo = restTemplate.exchange("http://localhost:8081/api/v1/auth/user-data/username-dept", HttpMethod.GET, entity, UserInfoDTO.class);

        requestService.updateApproval(uuid, userInfo.getBody().getUsername(), approvalDTO);

        return new ResponseEntity<>("success", HttpStatus.CREATED);
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
        ResponseEntity<DepartmentInfoDTO> deptInfo = restTemplate.exchange("http://localhost:8082/v1/api/users/departments/" + request.getDepartmentid(), HttpMethod.GET, entity, DepartmentInfoDTO.class);
        List<RequestDetailResponseDTO> details = new ArrayList<>();
        // get approval dept head
        ApprovalDTO approvalHeadDpt = null;
        if (request.getApprovaldpt() != null){
            ResponseEntity<ResponseApprovalDTO> data = restTemplate.exchange("http://localhost:8085/api/v1/approvals/" + request.getApprovaldpt(), HttpMethod.GET, entity, ResponseApprovalDTO.class);
            approvalHeadDpt = data.getBody().getData();
        }
        // get approval head fnc
        ApprovalDTO approvalHeadFnc = null;
        if (request.getApprovalfnc() != null){
            ResponseEntity<ResponseApprovalDTO> data = restTemplate.exchange("http://localhost:8085/api/v1/approvals/" + request.getApprovalfnc(), HttpMethod.GET, entity, ResponseApprovalDTO.class);
            approvalHeadFnc = data.getBody().getData();
        }
        // get type
        RequestTypeEntity type = requestTypeService.getTypeById(request.getRequesttypeid());
        for (RequestDetailEntity detail: request.getDetails()) {
            ServiceDTO serviceDTO = null;
            ItemDTO itemDTO = null;
            // get item
            if (detail.getItemid() != null){

                ResponseEntity<ItemEntityDTO> response = restTemplate.exchange("http://localhost:8083/api/v1/items/" + detail.getItemid(), HttpMethod.GET, entity, ItemEntityDTO.class);
                ItemEntityDTO item = response.getBody();
                itemDTO = new ItemDTO(item.getUuid(), item.getName());
            }
            // get service
            if (detail.getServiceid() != null){
                ResponseEntity<ServiceEntityDTO> response = restTemplate.exchange("http://localhost:8083/api/v1/services/" + detail.getItemid(), HttpMethod.GET, entity, ServiceEntityDTO.class);
                ServiceEntityDTO service = response.getBody();
                serviceDTO = new ServiceDTO(service.getUuid(), service.getName());
            }
            details.add(new RequestDetailResponseDTO(detail.getUuid(), itemDTO, serviceDTO, detail.getQty(), detail.getPrice(), detail.getDescription(), detail.getCreatedby(),  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(detail.getCreatedon()), detail.getLastupdatedby(),  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(detail.getLastupdatedon())));
        }
        return new RequestDTO(request.getUuid(), new TypeDTO(type.getUuid(), type.getType()), deptInfo.getBody(), request.getNotrans(), request.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), request.getDescription(), approvalHeadDpt, approvalHeadFnc, request.getCreatedby(), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(request.getCreatedon()), request.getLastupdatedby(), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(request.getLastupdatedon()), details);
    }
}
