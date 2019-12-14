package com.example.customerservice.event.sourcing;

import com.example.customerservice.event.DomainEvent;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;

public class EventSource implements ObservableOnSubscribe<DomainEvent> {

    private static Logger logger = LoggerFactory.getLogger(EventSource.class);

    private LinkedList<DomainEvent> eventQueue;
    private LinkedList<ObservableEmitter<DomainEvent>> emitters;


    public EventSource() {
        this.eventQueue = new LinkedList<>();
        this.emitters = new LinkedList<>();
    }

    public void send(DomainEvent event) {
        this.eventQueue.add(event);
        logger.debug("Sending event {} to {} subscribers", event, emitters.size());
        for (ObservableEmitter<DomainEvent> emitter : emitters) {
            try {
                if (! emitter.isDisposed()) {
                    emitter.onNext(event);
                }
            }catch (Throwable t){
            }
        }
    }

    @Override
    public void subscribe(ObservableEmitter<DomainEvent> emitter) {
        this.emitters.add(emitter);
        logger.debug("Added emitter, subscriber count is now {}", emitters.size());
    }
}
