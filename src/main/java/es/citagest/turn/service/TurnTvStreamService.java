package es.citagest.turn.service;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface TurnTvStreamService {
    SseEmitter subscribe();
    void publishTvUpdate(Object payload);
}
