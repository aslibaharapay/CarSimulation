package com.mercedes.vehicle.api.car.simulator.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.socket.messaging.StompSubProtocolErrorHandler;

@Slf4j
public class WebSocketErrorHandler extends StompSubProtocolErrorHandler {
    public WebSocketErrorHandler()
    {super();}

    @Override
    public Message<byte[]> handleClientMessageProcessingError(Message<byte[]> clientMessage, Throwable ex) {
        log.error("handleClientMessageProcessingError:clientMessage-" + clientMessage + ", error-"+ex.getMessage());
        return super.handleClientMessageProcessingError(clientMessage, ex);
    }

    @Override
    public Message<byte[]> handleErrorMessageToClient(Message<byte[]> errorMessage) {
        log.error("handleErrorMessageToClient:errorMessage-" + errorMessage);
        return super.handleErrorMessageToClient(errorMessage);
    }

    @Override
    protected Message<byte[]> handleInternal(StompHeaderAccessor errorHeaderAccessor, byte[] errorPayload, Throwable cause, StompHeaderAccessor clientHeaderAccessor) {
        log.error("handleInternal:errorHeaderAccessor-" + errorHeaderAccessor + ", errorPayload-" + errorPayload + ", error-" + cause.getMessage() + ", clientHeaderAccessor-" + clientHeaderAccessor);
        return super.handleInternal(errorHeaderAccessor, errorPayload, cause, clientHeaderAccessor);
    }
}
