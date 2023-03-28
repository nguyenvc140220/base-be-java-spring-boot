package com.metechvn.contacts.commands.handlers;

import com.metechvn.contacts.commands.SaveContactsFileCommand;
import com.metechvn.contacts.entities.ContactsFileEntity;
import com.metechvn.contacts.repositories.ContactsFileEntityRepository;
import lombok.RequiredArgsConstructor;
import luongdev.cqrs.RequestHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SaveContactsFileHandler implements RequestHandler<ContactsFileEntity, SaveContactsFileCommand> {

    private final ContactsFileEntityRepository contactsFileEntityRepository;

    @Override
    public ContactsFileEntity handle(SaveContactsFileCommand cmd) {
        var dynamicEntityType = ContactsFileEntity.builder()
                .fileName(cmd.getFileName())
                .filePath(cmd.getFilePath())
                .importStatus("PROCESS")
                .build();
        return contactsFileEntityRepository.save(dynamicEntityType);
    }
}
