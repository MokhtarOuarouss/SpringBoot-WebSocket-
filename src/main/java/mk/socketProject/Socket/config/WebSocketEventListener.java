package mk.socketProject.Socket.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mk.socketProject.Socket.entity.Message;
import mk.socketProject.Socket.entity.MessageType;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Optional;

@Component
@Slf4j
public class WebSocketEventListener {

    private final SimpMessageSendingOperations messageTemplate;

    public WebSocketEventListener(SimpMessageSendingOperations messageTemplate) {
        this.messageTemplate = messageTemplate;
    }

    @EventListener
    public void handlWebSocketDisconnectListener(SessionDisconnectEvent event){
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) headerAccessor.getSessionAttributes().get("username");

        if(username != null){
            log.info("User disconnected : {}",username);
            var message = Message.builder()
                    .type(MessageType.LEAVE)
                    .sender(username)
                    .build();

            messageTemplate.convertAndSend("/topic/public", Optional.ofNullable(message));
        }
    }
}
