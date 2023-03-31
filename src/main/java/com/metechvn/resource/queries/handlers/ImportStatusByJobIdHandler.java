package com.metechvn.resource.queries.handlers;

import com.metechvn.common.util.DataUtil;
import com.metechvn.resource.dtos.ImportStatusDto;
import com.metechvn.resource.queries.ImportStatusByJobIdQuery;
import com.metechvn.resource.repositories.ImportFileRepository;
import lombok.RequiredArgsConstructor;
import luongdev.cqrs.RequestHandler;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ImportStatusByJobIdHandler implements RequestHandler<ImportStatusDto, ImportStatusByJobIdQuery> {

    private final ImportFileRepository importFileRepository;

    @Override
    public ImportStatusDto handle(ImportStatusByJobIdQuery query) {
        var importFile = importFileRepository.findIncludeStatusById(UUID.fromString(query.getJobId()));
        if (importFile == null || importFile.getImportStatus() == null) return null;

        var status = importFile.getImportStatus();


        var builder = ImportStatusDto.builder()
                .jobId(query.getJobId())
                .fileName(importFile.getFileName())
                .totalRows(status.getTotalRows())
                .errorRows(status.getErrorRows())
                .successRows(status.getSuccessRows());

        if (status.getTotalRows() > 0) {
            builder
                    .successRatio(DataUtil.round(status.getSuccessRows() / (double) status.getTotalRows(), 2))
                    .errorRatio(DataUtil.round(status.getErrorRows() / (double) status.getTotalRows(), 2));
        }

        return builder.build();
    }

}
