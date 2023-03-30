package com.metechvn.contacts.controllers;

import com.metechvn.common.BaseResponse;
import com.metechvn.contacts.commands.AddHeaderMappingCommand;
import com.metechvn.contacts.commands.SaveContactsFileCommand;
import com.metechvn.dynamic.commands.EntityTypeAddPropertyCommand;
import com.metechvn.exception.BusinessException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import luongdev.cqrs.Bus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.nio.file.Paths;
import java.text.Normalizer;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1.0/contacts")
public class ImportContactsController {
    private final Bus bus;
    @Value("${file.upload}")
    private String filePath;


    @PostMapping({"/import"})
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(method = RequestMethod.POST, consumes = {"multipart/form-data"},value = "/import")
    public BaseResponse<Boolean> ImportContacts(@RequestParam("file") MultipartFile file) {
        try {
            String fileName= file.getOriginalFilename().replaceAll("\\s+","_");
            fileName = Normalizer.normalize(fileName, Normalizer.Form.NFD);
            fileName = fileName.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
            String fileLocation = Paths.get(filePath,"uploads", "import", "contacts",fileName).toString();
            file.transferTo(new File(fileLocation));
            SaveContactsFileCommand cmd = new SaveContactsFileCommand();
            cmd.setFilePath(Paths.get("uploads", "import", "contacts",fileName).toString());
            cmd.setFileName(fileName);
            System.out.println(bus.execute(cmd));
        } catch (Exception e){
            throw new BusinessException(String.format("Upload %s không thành công!",e));
        }
        return BaseResponse.onOk(true);
    }

    @PostMapping({"/add-header-mapping"})
    public BaseResponse<Boolean> AddHeaderMapping(@Valid @RequestBody AddHeaderMappingCommand cmd) {
        System.out.println(bus.execute(cmd));
        return BaseResponse.onOk(true);
    }
}
