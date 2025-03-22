package com.activemq.controller;

import com.activemq.service.MessageService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/jms")
public class JmsController {

    private final MessageService messageService;

    public JmsController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/send")
    public String sendMessage(@RequestParam String queue,
                              @RequestParam String message) {
        messageService.send(queue, message);
        return "Message sent to queue: " + queue;
    }
}
