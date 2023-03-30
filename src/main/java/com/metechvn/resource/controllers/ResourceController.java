package com.metechvn.resource.controllers;

import com.metechvn.resource.dtos.ImportStatusDto;
import com.metechvn.resource.queries.ImportStatusByJobIdQuery;
import lombok.RequiredArgsConstructor;
import luongdev.cqrs.Bus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1.0/resource")
public class ResourceController {

    private final Bus bus;

    @GetMapping("/import-status")
    public ImportStatusDto getImportStatus(@RequestParam("job") String jobId) {
        return bus.execute(new ImportStatusByJobIdQuery(jobId));
    }

}
