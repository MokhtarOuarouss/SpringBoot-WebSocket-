package mk.socketProject.Socket.chat;


import mk.socketProject.Socket.entity.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {
    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public Message sendMessage(
           @Payload Message meessage
    ){
        return meessage;
    }

    @MessageMapping("/chat.AddUser")
    @SendTo("/topic/public")
    public Message AddUser(
            @Payload Message message,
            SimpMessageHeaderAccessor headerAccerssor
    ){
        // Add username in web socket session
        headerAccerssor.getSessionAttributes().put("username",message.getSender());
        return message;
    }
}
