package com.metechvn.contacts.eventlisteners;

import com.metechvn.contacts.commands.CreateSegmentationCommand;
import com.metechvn.contacts.events.ContactUploadEvent;
import com.metechvn.filter.Filter;
import com.metechvn.resource.entities.ImportFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import luongdev.cqrs.Bus;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ContactUploadEventHandler implements ApplicationListener<ContactUploadEvent> {

    private final Bus bus;

    @Override
    public void onApplicationEvent(@NotNull ContactUploadEvent event) {
        if (event.getSource() == null || !(event.getSource() instanceof ImportFile file)) {
            log.warn("Received event class {} without source. Ignored!", event.getClass().getName());

            return;
        }

        var cmd = CreateSegmentationCommand
                .builder()
                .name(file.getFileName())
                .filters(Filter.builder().field("source").operator("Eq").value(file.getFileName()).build())
                .build();

        var segmentation = bus.execute(cmd);
        if (segmentation != null) {
            log.info("Created segmentation {} for contact import file {}", cmd.getName(), file.getFileName());
        } else {
            log.error("Cannot create segmentation for contact import file {}", file.getFileName());
        }
    }
}
