package com.metechvn.contacts.commands.handlers;

import com.metechvn.contacts.commands.AddHeaderMappingCommand;
import com.metechvn.contacts.entities.ContactsFileEntity;
import com.metechvn.contacts.events.AddHeaderMappingEvent;
import com.metechvn.contacts.repositories.ContactsFileEntityRepository;
import com.metechvn.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import luongdev.cqrs.RequestHandler;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AddHeaderMappingHandler  implements RequestHandler<ContactsFileEntity, AddHeaderMappingCommand> {
    private final ContactsFileEntityRepository contactsFileEntityRepository;
    private final ApplicationEventPublisher applicationEventPublisher;
    @Override
    public ContactsFileEntity handle(AddHeaderMappingCommand addHeaderMappingCommand) {
        var existing =  contactsFileEntityRepository.findById(addHeaderMappingCommand.contactsFileId);
        if (existing.isEmpty())
            throw new BusinessException(String.format("ID contact file %s không tồn tại", addHeaderMappingCommand.contactsFileId));
        var entity = existing.get();
        entity.setHeaderMapping((addHeaderMappingCommand.headerMapping));
        applicationEventPublisher.publishEvent(new AddHeaderMappingEvent(entity));
        return contactsFileEntityRepository.save(entity);
    }
}
