package com.example.customerservice.command;

import com.example.customerservice.event.Event;
import com.google.common.eventbus.EventBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommandGateway {

    @Autowired
    private EventBus eventBus;

    public void send(Command cmd){
        Event event = cmd.execute();
        eventBus.post(event);
    }
}
