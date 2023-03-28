package com.metechvn.contacts.commands;

import com.metechvn.contacts.entities.ContactsFileEntity;
import lombok.*;
import luongdev.cqrs.Request;

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
