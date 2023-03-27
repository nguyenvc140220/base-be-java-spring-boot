package com.metechvn.contacts.controllers;

import com.metechvn.common.BaseResponse;
import com.metechvn.contacts.commands.SaveContactsFileCommand;
import com.metechvn.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import luongdev.cqrs.Bus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1.0/contacts")
public class ImportContactsController {
    private final Bus bus;
    @PostMapping({"/import"})
    @RequestMapping(method = RequestMethod.POST, consumes = {"multipart/form-data"})
    public BaseResponse<Boolean> ImportContacts(@RequestParam("file") MultipartFile file) {
        try {
            String fileName= file.getOriginalFilename();
            String fileLocation = new File("uploads\\import\\contacts").getAbsolutePath() + "\\" + fileName;
            file.transferTo(new File(fileLocation));
            SaveContactsFileCommand cmd = new SaveContactsFileCommand();
            cmd.filePath = fileLocation;
            cmd.fileName = fileName;
            System.out.println(bus.execute(cmd));
        } catch (Exception e){
            throw new BusinessException(String.format("Upload %s không thành công!",e));
        }
        return BaseResponse.onOk(true);
    }
}
