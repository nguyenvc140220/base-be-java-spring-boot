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

@Component
@RequiredArgsConstructor
public class AddHeaderMappingHandler  implements RequestHandler<ContactsFileEntity, AddHeaderMappingCommand> {
    private final ContactsFileEntityRepository contactsFileEntityRepository;
    private final ApplicationEventPublisher applicationEventPublisher;
    @Override
    public ContactsFileEntity handle(AddHeaderMappingCommand cmd) {
        var existing =  contactsFileEntityRepository.findById(cmd.contactsFileId);
        if (existing.isEmpty())
            throw new BusinessException(String.format("ID contact file %s không tồn tại", cmd.contactsFileId));
        var entity = existing.get();
        entity.setHeaderMapping((cmd.headerMapping));
        applicationEventPublisher.publishEvent(new AddHeaderMappingEvent(cmd));
        return contactsFileEntityRepository.save(entity);
    }
}
