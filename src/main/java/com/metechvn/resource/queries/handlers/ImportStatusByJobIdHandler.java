package com.metechvn.resource.queries.handlers;

import com.metechvn.common.util.DataUtil;
import com.metechvn.resource.dtos.ImportStatusDto;
import com.metechvn.resource.queries.ImportStatusByJobIdQuery;
import com.metechvn.resource.repositories.ImportStatusRepository;
import lombok.RequiredArgsConstructor;
import luongdev.cqrs.RequestHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ImportStatusByJobIdHandler implements RequestHandler<ImportStatusDto, ImportStatusByJobIdQuery> {

    private final ImportStatusRepository importStatusRepository;

    @Override
    public ImportStatusDto handle(ImportStatusByJobIdQuery query) {
        var status = importStatusRepository.findByJobId(query.getJobId());
        if (status == null) return null;

        var builder = ImportStatusDto.builder()
                .jobId(query.getJobId())
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
