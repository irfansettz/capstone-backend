//package com.capstonebackend.notificationservice.controller;
//
//import com.capstonebackend.notificationservice.dto.EmailDTO;
//import com.capstonebackend.notificationservice.service.EmailService;
//import jakarta.mail.MessagingException;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("api/v1/send-email")
//public class EmailController {
//
//    @Autowired
//    private EmailService emailService;
//
//    @RabbitListener(queues = "${rabbitmq.queues.notification}")
//    @PostMapping
//    public ResponseEntity<String> sendEmail(@RequestBody EmailDTO emailDTO) throws MessagingException {
//        emailService.sendEmailMessage(emailDTO.getTo(), emailDTO.getSubject(), emailDTO.getBody());
//        return ResponseEntity.ok("Email sent successfully");
//    }
//
//}
