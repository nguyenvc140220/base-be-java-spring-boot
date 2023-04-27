package com.metechvn.contacts.controllers;

import com.metechvn.common.BaseResponse;
import com.metechvn.common.excel.ExcelUtil;
import com.metechvn.contacts.commands.AddHeaderMappingCommand;
import com.metechvn.contacts.commands.SaveContactsFileCommand;
import com.metechvn.contacts.events.ContactUploadEvent;
import com.metechvn.resource.entities.ImportFile;
import com.metechvn.exception.BusinessException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import luongdev.cqrs.Bus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1.0/contacts")
public class ImportContactsController {
    private final Bus bus;
    @Value("${file.upload}")
    private String filePath;

    private final DateFormat df = new SimpleDateFormat("yyMMddHHmmssS");

    private final ApplicationEventPublisher publisher;


    @PostMapping({"/import"})
    @RequestMapping(method = RequestMethod.POST, consumes = {"multipart/form-data"}, value = "/import")
    public BaseResponse<ImportFile> ImportContacts(@RequestParam("file") MultipartFile file) {
        try {
            var fileName = Objects.requireNonNull(file.getOriginalFilename()).replaceAll("\\s+", "_");
            fileName = Normalizer.normalize(fileName, Normalizer.Form.NFD);
            fileName = String.format(
                    "%s__%s",
                    df.format(new Date()),
                    fileName.replaceAll("\\p{InCombiningDiacriticalMarks}", "")
            );
            File theDir = new File( Paths.get(filePath, "uploads", "import", "contacts").toString());
            if (!theDir.exists()){
                theDir.mkdirs();
            }
            var fileLocation = Paths.get(filePath, "uploads", "import", "contacts", fileName).toString();
            file.transferTo(new File(fileLocation));
            var cmd = new SaveContactsFileCommand();
            cmd.setFilePath(Paths.get("uploads", "import", "contacts", fileName).toString());
            cmd.setFileName(fileName);
            var contactFile = bus.execute(cmd);

            publisher.publishEvent(new ContactUploadEvent(contactFile));

            return BaseResponse.onCreated(contactFile);
        } catch (Exception e) {
            throw new BusinessException(String.format("Upload %s không thành công!", e));
        }
    }

    @PostMapping({"/add-header-mapping"})
    public BaseResponse<Boolean> AddHeaderMapping(@Valid @RequestBody AddHeaderMappingCommand cmd) {
        System.out.println(bus.execute(cmd));
        return BaseResponse.onOk(true);
    }
    @GetMapping({"/get-example-file-import"})
    public BaseResponse<Boolean> GetExampleFileImport(@Valid @RequestBody AddHeaderMappingCommand cmd) {
        return BaseResponse.onOk(true);
    }

}
