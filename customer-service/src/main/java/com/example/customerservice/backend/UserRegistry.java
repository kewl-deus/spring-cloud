package com.example.customerservice.backend;

import com.example.customerservice.event.CustomerRegistrationDataValidatedEvent;
import com.example.customerservice.model.valueobject.CustomerIdentifier;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.springframework.stereotype.Component;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

@Component
public class UserRegistry {

    private EventBus eventBus;

    public UserRegistry(EventBus eventBus) {
        this.eventBus = eventBus;
        eventBus.register(this);
    }

    public void register(CustomerIdentifier externalId, CustomerIdentifier internalId){
        throw new NotImplementedException();
    }

    @Subscribe
    public void handleEvent(CustomerRegistrationDataValidatedEvent event){
        this.register(event.getExternalIdentifier(), event.getInternalIdentifier());
    }
}
