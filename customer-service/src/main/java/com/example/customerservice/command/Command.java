package com.example.customerservice.command;

import com.example.customerservice.event.CustomerEvent;

/**
 * Command is where the decision is made, but no state is changed.
 */
public interface Command {

    CustomerEvent execute();
}
