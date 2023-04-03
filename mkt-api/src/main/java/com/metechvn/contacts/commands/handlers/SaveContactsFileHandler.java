package com.metechvn.contacts.commands.handlers;

import com.metechvn.contacts.commands.SaveContactsFileCommand;
import com.metechvn.resource.entities.ImportFile;
import com.metechvn.resource.repositories.ImportFileRepository;
import lombok.RequiredArgsConstructor;
import luongdev.cqrs.RequestHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SaveContactsFileHandler implements RequestHandler<ImportFile, SaveContactsFileCommand> {

    private final ImportFileRepository importFileRepository;

    @Override
    public ImportFile handle(SaveContactsFileCommand cmd) {
        var dynamicEntityType = ImportFile.builder()
                .fileName(cmd.getFileName())
                .filePath(cmd.getFilePath())
                .build();
        return importFileRepository.save(dynamicEntityType);
    }
}
