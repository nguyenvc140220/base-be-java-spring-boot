package com.metechvn.contacts.commands.handlers;

import com.metechvn.contacts.commands.AddHeaderMappingCommand;
import com.metechvn.contacts.entities.ContactsFileEntity;
import com.metechvn.contacts.repositories.ContactsFileEntityRepository;
import com.metechvn.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import luongdev.cqrs.RequestHandler;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AddHeaderMappingHandler  implements RequestHandler<ContactsFileEntity, AddHeaderMappingCommand> {
    private final ContactsFileEntityRepository contactsFileEntityRepository;
    @Override
    public ContactsFileEntity handle(AddHeaderMappingCommand addHeaderMappingCommand) {
        var existing =  contactsFileEntityRepository.existsById(addHeaderMappingCommand.contactsFileId);
        if (!existing)
            throw new BusinessException(String.format("ID contact file %s không tồn tại", addHeaderMappingCommand.contactsFileId));
        contactsFileEntityRepository.updateById(addHeaderMappingCommand.headerMapping,addHeaderMappingCommand.contactsFileId);
        return null;
    }
}
