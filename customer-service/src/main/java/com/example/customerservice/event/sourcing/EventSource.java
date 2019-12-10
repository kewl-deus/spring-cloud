package com.example.customerservice.event.sourcing;

import com.example.customerservice.event.DomainEvent;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

import java.util.LinkedList;

public class EventSource implements ObservableOnSubscribe<DomainEvent> {

    private LinkedList<DomainEvent> eventQueue;
    private LinkedList<ObservableEmitter<DomainEvent>> emitters;


    public EventSource() {
        this.eventQueue = new LinkedList<>();
        this.emitters = new LinkedList<>();
    }

    public void send(DomainEvent event) {
        this.eventQueue.add(event);
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
    public void subscribe(ObservableEmitter<DomainEvent> emitter) throws Exception {
        this.emitters.add(emitter);
    }
}
