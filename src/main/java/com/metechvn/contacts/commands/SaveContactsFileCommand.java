package com.metechvn.contacts.commands;

import com.metechvn.contacts.entities.ContactsFileEntity;
import com.metechvn.dynamic.commands.CreateEntityCommand;
import lombok.*;
import luongdev.cqrs.Request;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaveContactsFileCommand  implements Request<ContactsFileEntity> {

    public String fileName;
    public String importStatus;
    public Long totalRecords;
    public Long totalRecordsSuccess ;
    public String headerMapping;
    public String filePath;
    public String tenant;
}
