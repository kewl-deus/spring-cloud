package com.example.customerservice.command;

import com.example.customerservice.event.CustomerEvent;

public interface Command {

    CustomerEvent execute();
}
