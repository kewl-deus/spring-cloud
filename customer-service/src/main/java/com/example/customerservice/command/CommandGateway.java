package com.example.customerservice.command;

import com.example.customerservice.event.CustomerEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.bus.Event;
import reactor.bus.EventBus;

@Component
public class CommandGateway {

    @Autowired
    private EventBus eventBus;

    public CustomerEvent send(Command cmd){
        CustomerEvent event = cmd.execute();
        eventBus.notify(event.getClass().getSimpleName(), new Event<>(event));
        return event;
    }
}
