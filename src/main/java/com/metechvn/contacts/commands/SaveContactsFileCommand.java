package com.metechvn.contacts.commands;

import com.metechvn.contacts.entities.ContactsFileEntity;
import luongdev.cqrs.Request;

public class SaveContactsFileCommand  implements Request<ContactsFileEntity> {

    public String fileName;
    public String importStatus;
    public Long totalRecords;
    public Long totalRecordsSuccess ;
    public String headerMapping;
    public String filePath;
    public String tenant;
}
