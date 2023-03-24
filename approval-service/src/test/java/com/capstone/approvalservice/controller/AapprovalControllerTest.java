package com.capstone.approvalservice.controller;

import com.capstone.approvalservice.dto.*;
import com.capstone.approvalservice.entity.ApprovalEntity;
import com.capstone.approvalservice.service.ApprovalService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ExtendWith(MockitoExtension.class)
public class AapprovalControllerTest {
    private MockMvc mockMvc;

    @InjectMocks
    private ApprovalController approvalController;

    @BeforeEach
    private void setup(){
        mockMvc = standaloneSetup(approvalController).build();
    }
    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ApprovalService approvalService;

    @Test
    public void createApprovalModuleNonFnc() throws Exception{
        AddApprovalDTO approvalDTO = new AddApprovalDTO("uuuu", "ApprovalHeadDpt", "exp");

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth("token");
        HttpEntity<String> entity = new HttpEntity<>(headers);

        UserInfoDTO userInfo = new UserInfoDTO("username", "dddd");
        ApprovalModuleDTO approvalModule = new ApprovalModuleDTO(1L, "mmmm", "m m m m", "exp mmmmm");
        DepartmentInfoDTO departmentInfo = new DepartmentInfoDTO(1L, "dddd", "dept", "deprt");

        when(restTemplate.exchange(
                eq("http://auth-service:8081/api/v1/auth/user-data/username-dept"),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(UserInfoDTO.class)
        )).thenReturn(new ResponseEntity<>(userInfo, HttpStatus.OK));

        when(restTemplate.exchange(
                eq("http://management-user-service:8082/v1/api/users/approval-module?name=" + approvalDTO.getModulename()),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(ApprovalModuleDTO.class)
        )).thenReturn(new ResponseEntity<>(approvalModule, HttpStatus.OK));

        when(restTemplate.exchange(
                eq("http://management-user-service:8082/v1/api/users/departments/uuid/" + userInfo.getDepartmentuuid()),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(DepartmentInfoDTO.class)
        )).thenReturn(new ResponseEntity<>(departmentInfo, HttpStatus.OK));

        ApprovalEntity approvalSave = new ApprovalEntity(null, "aaaa", departmentInfo.getId(), approvalModule.getId(), approvalDTO.getExplain(), "PENDING", userInfo.getUsername(), null, userInfo.getUsername(), null);

        when(approvalService.addApproval(approvalSave)).thenReturn(1L);

        when(restTemplate.exchange(
                eq("http://request-service:8084/v1/api/requests/approval/" + approvalDTO.getRequestuuid()),
                eq(HttpMethod.PUT),
                any(HttpEntity.class),
                eq(String.class)
        )).thenReturn(new ResponseEntity<>("success", HttpStatus.OK));

        ResponseDTO<String> response = new ResponseDTO<>();
        response.setHttpStatus(HttpStatus.CREATED);
        response.setMessage("created");
        response.setData(approvalSave.getUuid());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/approvals/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsString(approvalDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(response)));
    }

}
