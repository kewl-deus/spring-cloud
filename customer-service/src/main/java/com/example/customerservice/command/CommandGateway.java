package com.example.customerservice.command;

import com.example.customerservice.event.DomainEvent;
import org.springframework.stereotype.Component;
import reactor.bus.Event;
import reactor.bus.EventBus;

@Component
public class CommandGateway {

    private EventBus eventBus;


    public CommandGateway(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    public DomainEvent send(Command cmd){
        DomainEvent event = cmd.execute();
        eventBus.notify(event.getClass(), new Event<>(event));
        return event;
    }
}
