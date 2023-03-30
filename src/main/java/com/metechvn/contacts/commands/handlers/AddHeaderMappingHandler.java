package com.metechvn.contacts.commands.handlers;

import com.metechvn.contacts.commands.AddHeaderMappingCommand;
import com.metechvn.resource.entities.ImportFile;
import com.metechvn.contacts.events.AddHeaderMappingEvent;
import com.metechvn.resource.repositories.ImportFileRepository;
import com.metechvn.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import luongdev.cqrs.RequestHandler;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AddHeaderMappingHandler  implements RequestHandler<ImportFile, AddHeaderMappingCommand> {
    private final ImportFileRepository importFileRepository;
    private final ApplicationEventPublisher applicationEventPublisher;
    @Override
    public ImportFile handle(AddHeaderMappingCommand cmd) {
        var existing =  importFileRepository.findById(cmd.contactsFileId);
        if (existing.isEmpty())
            throw new BusinessException(String.format("ID contact file %s không tồn tại", cmd.contactsFileId));
        var entity = existing.get();
        entity.setHeaderMapping((cmd.headerMapping));
        applicationEventPublisher.publishEvent(new AddHeaderMappingEvent(cmd));
        return importFileRepository.save(entity);
    }
}
