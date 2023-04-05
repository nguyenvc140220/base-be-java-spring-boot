package com.metechvn.contacts.commands.handlers;

import com.metechvn.contacts.commands.DeleteSegmentationCommand;
import com.metechvn.contacts.events.SegmentationDeletedEvent;
import com.metechvn.contacts.repositories.SegmentationRepository;
import lombok.RequiredArgsConstructor;
import luongdev.cqrs.RequestHandler;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteSegmentationHandler implements RequestHandler<Boolean, DeleteSegmentationCommand> {

    private final ApplicationEventPublisher publisher;
    private final SegmentationRepository segmentationRepository;

    @Override
    public Boolean handle(DeleteSegmentationCommand cmd) {
        if (cmd.getId() == null) return false;

        var segmentation = segmentationRepository.findIncludeFiltersById(cmd.getId());
        if (segmentation == null) return false;

        segmentationRepository.delete(segmentation);

        publisher.publishEvent(new SegmentationDeletedEvent(segmentation));

        return true;
    }
}
