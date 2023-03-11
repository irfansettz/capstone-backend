package com.capstone.requestservice.controller;

import com.capstone.requestservice.dto.ResponseTypeDTO;
import com.capstone.requestservice.dto.TypeDTO;
import com.capstone.requestservice.entity.RequestTypeEntity;
import com.capstone.requestservice.repository.RequestTypeRepository;
import com.capstone.requestservice.service.RequestTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/api/requesttypes")
@RequiredArgsConstructor
public class RequestTypeController {
    private final RequestTypeService requestTypeService;

    @GetMapping
    public ResponseEntity<ResponseTypeDTO> getAllRequestType(){
        List<RequestTypeEntity> requestTypeEntities = requestTypeService.getAllRequestType();
        System.out.println(requestTypeEntities);
        List<TypeDTO> typeDTOS = new ArrayList<>();

        for (RequestTypeEntity type: requestTypeEntities) {
            typeDTOS.add(new TypeDTO(type.getUuid(), type.getType()));
        }

        ResponseTypeDTO response = new ResponseTypeDTO();
        response.setData(typeDTOS);
        response.setCode(200);
        response.setStatus("success");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ResponseTypeDTO> getRequestTypeByUuid(@PathVariable String uuid){
        RequestTypeEntity requestTypeEntity = requestTypeService.getRequestTypeByUuid(uuid);

        ResponseTypeDTO response = new ResponseTypeDTO(List.of(new TypeDTO(requestTypeEntity.getUuid(), requestTypeEntity.getType())));
        response.setCode(200);
        response.setStatus("success");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
