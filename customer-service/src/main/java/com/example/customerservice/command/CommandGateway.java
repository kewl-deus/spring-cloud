package com.example.customerservice.engine;

import com.example.customerservice.command.Command;
import org.springframework.stereotype.Component;

@Component
public class CommandGateway {

    public void send(Command cmd){
        cmd.execute();
    }
}
