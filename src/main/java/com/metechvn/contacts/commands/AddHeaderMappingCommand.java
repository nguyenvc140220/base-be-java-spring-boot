package com.metechvn.contacts.commands;

import com.metechvn.resource.entities.ImportFile;
import lombok.*;
import luongdev.cqrs.Request;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddHeaderMappingCommand implements Request<ImportFile> {
    public UUID contactsFileId;
    public String headerMapping;
    private final Map<String, Object> headers = new HashMap<>();
}
