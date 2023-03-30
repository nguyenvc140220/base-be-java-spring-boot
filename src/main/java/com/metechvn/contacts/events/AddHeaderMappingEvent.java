package com.metechvn.contacts.events;

import com.metechvn.contacts.commands.AddHeaderMappingCommand;
import org.springframework.context.ApplicationEvent;

public class AddHeaderMappingEvent extends ApplicationEvent {
    public AddHeaderMappingEvent(AddHeaderMappingCommand mappingCommand) {
        super(mappingCommand);
    }
}


