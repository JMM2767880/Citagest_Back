package es.citagest.turn.service.impl;

import es.citagest.turn.service.TurnTvStreamService;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class TurnTvStreamServiceImpl implements TurnTvStreamService {

    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    @Override
    public SseEmitter subscribe() {
        SseEmitter emitter = new SseEmitter(0L);
        emitters.add(emitter);

        System.out.println("TV suscrita. Total emitters: " + emitters.size());

        emitter.onCompletion(() -> {
            emitters.remove(emitter);
            System.out.println("Emitter completado. Total emitters: " + emitters.size());
        });

        emitter.onTimeout(() -> {
            emitters.remove(emitter);
            System.out.println("Emitter timeout. Total emitters: " + emitters.size());
        });

        emitter.onError((ex) -> {
            emitters.remove(emitter);
            System.out.println("Emitter error. Total emitters: " + emitters.size());
            ex.printStackTrace();
        });

        try {
            emitter.send(SseEmitter.event()
                    .name("connected")
                    .data("connected"));
        } catch (IOException e) {
            emitters.remove(emitter);
            e.printStackTrace();
        }

        return emitter;
    }


    public void publishTvUpdate(Object payload) {
        System.out.println("Publicando actualización TV. Emitters activos: " + emitters.size());

        List<SseEmitter> emittersToRemove = new CopyOnWriteArrayList<>();

        for (SseEmitter emitter : emitters) {
            try {
                emitter.send(
                        SseEmitter.event()
                                .name("turn-update")
                                .data(payload)
                );
            } catch (IOException e) {
                emittersToRemove.add(emitter);
                e.printStackTrace();
            }
        }

        emitters.removeAll(emittersToRemove);
    }
}