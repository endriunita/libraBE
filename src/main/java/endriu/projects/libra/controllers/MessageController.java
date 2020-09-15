package endriu.projects.libra.controllers;

import endriu.projects.libra.dao.ChatHistoryDao;
import endriu.projects.libra.model.Message;
import endriu.projects.libra.model.exceptions.InexistentUserException;
import endriu.projects.libra.services.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class MessageController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/chat/{to}")
    @CrossOrigin(origins = "http://localhost:3000")
    public void sendMessage(@DestinationVariable String to, Message message) {
        System.out.println("received message " + "\"" + "\"" + message.getMessage() + " from: " + message.getSender());

        simpMessagingTemplate.convertAndSend("/topic/messages/" + to, message);
    }
}
