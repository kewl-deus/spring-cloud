package com.example.customerservice.command;

import com.example.customerservice.event.Event;

public interface Command {

    Event execute();
}
