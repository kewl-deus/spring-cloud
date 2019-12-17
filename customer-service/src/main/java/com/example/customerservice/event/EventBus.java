package com.example.customerservice.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.context.PayloadApplicationEvent;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Component
public class EventBus implements ApplicationListener<PayloadApplicationEvent<? extends DomainEvent>> {

    private final ApplicationEventPublisher applicationEventPublisher;
    private List<DomainEventListener> listeners;

    public EventBus(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
        this.listeners = new LinkedList<>();
    }

    public void send(DomainEvent domainEvent) {
        this.applicationEventPublisher.publishEvent(domainEvent);
    }

    public <T extends DomainEvent> void register(Class<T> eventType, Consumer<T> consumer) {
        this.register(eventType, consumer, DomainEventListener.INFINITY);
    }

    public <T extends DomainEvent> void register(Class<T> eventType, Consumer<T> consumer, long times) {
        if (times < 1) throw new IllegalArgumentException("Must register at least for one invocation");
        this.listeners.add(new DomainEventListener<T>(eventType, consumer, times));
    }

    public <T extends DomainEvent> boolean unregister(Consumer<T> consumer) {
        return this.listeners.removeIf(l -> l.consumer == consumer);
    }

    @Override
    public void onApplicationEvent(PayloadApplicationEvent<? extends DomainEvent> event) {
        List<DomainEventListener> workList = Collections.unmodifiableList(this.listeners);
        for (DomainEventListener listener : workList) {
            try {
                listener.accept(event.getPayload());
            } finally {
                if (! listener.hasInvocationsLeft()){
                    this.listeners.remove(listener);
                }
            }
        }
    }

    private class DomainEventListener<T extends DomainEvent> {
        private static final long INFINITY = Long.MAX_VALUE;
        private Class<T> eventType;
        private Consumer<T> consumer;
        private long times;
        private long invocationCounter;

        private DomainEventListener(Class<T> eventType, Consumer<T> consumer) {
            this(eventType, consumer, INFINITY);
        }

        public DomainEventListener(Class<T> eventType, Consumer<T> consumer, long times) {
            this.eventType = eventType;
            this.consumer = consumer;
            this.times = times;
            this.invocationCounter = times;
        }

        public void accept(T t) {
            if (accepts(t) && hasInvocationsLeft()) {
                try {
                    consumer.accept(t);
                } finally {
                    if (times != INFINITY) {
                        --invocationCounter;
                    }
                }
            }
        }

        private boolean hasInvocationsLeft() {
            return this.invocationCounter > 0;
        }

        private boolean accepts(T event) {
            return eventType.isAssignableFrom(event.getClass());
        }
    }
}
