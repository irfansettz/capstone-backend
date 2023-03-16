package com.capstone.approvalservice.controller;

import com.capstone.approvalservice.dto.*;
import com.capstone.approvalservice.entity.ApprovalEntity;
import com.capstone.approvalservice.service.ApprovalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/approvals")
@RequiredArgsConstructor
public class ApprovalController {
    private final ApprovalService approvalService;
    private final RestTemplate restTemplate;

    @PostMapping
    @Transactional
    public ResponseEntity<ResponseDTO<String>> createApproval(@RequestBody AddApprovalDTO approval, @RequestHeader("Authorization") String token){
        String newToken = token.split(" ")[1];
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(newToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<UserInfoDTO> userInfo = restTemplate.exchange("http://auth-service:8081/api/v1/auth/user-data/username-dept", HttpMethod.GET, entity, UserInfoDTO.class);
        ResponseEntity<ApprovalModuleDTO> approvalMdl = restTemplate.exchange("http://management-user-service:8082/v1/api/users/approval-module?name=" + approval.getModulename(), HttpMethod.GET, entity, ApprovalModuleDTO.class);

        DepartmentInfoDTO deptInfo = null;
        if (approvalMdl.getBody().getName().equals("ApprovalHeadFNC")){
            ResponseEntity<DepartmentInfoDTO> response = restTemplate.exchange("http://management-user-service:8082/v1/api/users/departments/code/FNC", HttpMethod.GET, entity, DepartmentInfoDTO.class);
            deptInfo = response.getBody();
        } else {
            ResponseEntity<DepartmentInfoDTO> response = restTemplate.exchange("http://management-user-service:8082/v1/api/users/departments/uuid/" + userInfo.getBody().getDepartmentuuid(), HttpMethod.GET, entity, DepartmentInfoDTO.class);
            deptInfo = response.getBody();
        }
        String uuid = UUID.randomUUID().toString();
        ApprovalEntity approvalSave = new ApprovalEntity(null, uuid, deptInfo.getId(), approvalMdl.getBody().getId(), approval.getExplain(), "PENDING", userInfo.getBody().getUsername(), null, userInfo.getBody().getUsername(), null);

        Long approvalid = approvalService.addApproval(approvalSave);

        // update request
        HttpEntity<UpdateApprovalDTO> aprovalUpdate = new HttpEntity<>(new UpdateApprovalDTO(approvalid, approval.getModulename()),headers);
        ResponseEntity<String> request = restTemplate.exchange("http://request-service:8084/v1/api/requests/approval/" + approval.getRequestuuid(), HttpMethod.PUT, aprovalUpdate, String.class);
        ResponseDTO<String> response = new ResponseDTO<>();
        response.setHttpStatus(HttpStatus.CREATED);
        response.setMessage("created");
        response.setData(uuid);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{uuid}")
    @Transactional
    public ResponseEntity<ResponseDTO<String>> updateStatusApproval(@RequestBody StatusDTO status, @RequestHeader("Authorization") String token, @PathVariable String uuid){
        String newToken = token.split(" ")[1];
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(newToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<UserInfoDTO> userInfo = restTemplate.exchange("http://auth-service:8081/api/v1/auth/user-data/username-dept", HttpMethod.GET, entity, UserInfoDTO.class);

        approvalService.updateStatusByUuid(uuid, userInfo.getBody().getUsername(), status);

        return new ResponseEntity<>(new ResponseDTO<>(HttpStatus.CREATED, "updated", "success"), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<ApprovalDTO>> getByApprovalUuid(@PathVariable Long id){
        ApprovalEntity approval = approvalService.getById(id);

        return new ResponseEntity<>(new ResponseDTO<>(HttpStatus.OK, "success", getDataApproval(approval)), HttpStatus.OK);
    }

    @GetMapping("uuid/{uuid}")
    public ResponseEntity<ResponseDTO<ApprovalDTO>> getByApprovalUuid(@PathVariable String uuid){
        ApprovalEntity approval = approvalService.getByUuid(uuid);

        return new ResponseEntity<>(new ResponseDTO<>(HttpStatus.OK, "success", getDataApproval(approval)), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ResponseDTO<List<ApprovalDTO>>> getAllApproval(@RequestParam(name = "departmentuuid", required = false) String departmentuuid){
        List<ApprovalDTO> data = new ArrayList<>();
        if (departmentuuid != null){
            HttpHeaders headers = new HttpHeaders();
            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<DepartmentInfoDTO> deptInfo = restTemplate.exchange("http://management-user-service:8082/v1/api/users/departments/uuid/" + departmentuuid, HttpMethod.GET, entity, DepartmentInfoDTO.class);
            List<ApprovalEntity> approvals = approvalService.getByDepartmentId(deptInfo.getBody().getId());
            for (ApprovalEntity approval:approvals) {
                data.add(getDataApproval(approval));
            }
        }  else {
            List<ApprovalEntity> approvals = approvalService.getAllApproval();
            for (ApprovalEntity approval:approvals) {
                data.add(getDataApproval(approval));
            }
        }
        return new ResponseEntity<>(new ResponseDTO<>(HttpStatus.OK, "success", data),HttpStatus.OK);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<ResponseDTO> deleteApprovalByUuid(@PathVariable String uuid){
        approvalService.deleteByUuid(uuid);

        return new ResponseEntity<>(new ResponseDTO<>(HttpStatus.OK, "success", null), HttpStatus.OK);
    }
    public ApprovalDTO getDataApproval(ApprovalEntity approval){
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<DepartmentInfoDTO> deptInfo = restTemplate.exchange("http://management-user-service:8082/v1/api/users/departments/" + approval.getDepartmentid(), HttpMethod.GET, entity, DepartmentInfoDTO.class);
        ResponseEntity<ApprovalModuleDTO> approvalMdl = restTemplate.exchange("http://management-user-service:8082/v1/api/users/approval-module/" + approval.getModuleid(), HttpMethod.GET, entity, ApprovalModuleDTO.class);

        return new ApprovalDTO(approval.getId(), approval.getUuid(), approvalMdl.getBody(), deptInfo.getBody(), approval.getDescription(), approval.getStatus(), approval.getCreatedby(), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(approval.getCreatedon()), approval.getLastupdatedby(), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(approval.getLastupdatedon()));
    }
}
