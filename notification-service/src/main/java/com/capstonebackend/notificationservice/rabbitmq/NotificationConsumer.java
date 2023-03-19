package com.capstonebackend.notificationservice.rabbitmq;

import com.capstonebackend.notificationservice.dto.EmailDTO;
import com.capstonebackend.notificationservice.service.EmailService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class NotificationConsumer {

    private final EmailService emailService;

    @RabbitListener(queues = "${rabbitmq.queues.notification}")
    public void consumer(EmailDTO email) {
        //log.info("Consumed {} from queue", notificationRequest);
        emailService.sendEmailMessage(email.getTo(), email.getSubject(), email.getBody());
    }
}