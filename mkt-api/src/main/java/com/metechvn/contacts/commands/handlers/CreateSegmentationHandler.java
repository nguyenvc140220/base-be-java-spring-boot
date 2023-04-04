package com.metechvn.contacts.commands.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.metechvn.contacts.commands.CreateSegmentationCommand;
import com.metechvn.contacts.entities.Segmentation;
import com.metechvn.contacts.entities.SegmentationFilter;
import com.metechvn.contacts.repositories.SegmentationFilterRepository;
import com.metechvn.contacts.repositories.SegmentationRepository;
import com.metechvn.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import luongdev.cqrs.RequestHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreateSegmentationHandler implements RequestHandler<Segmentation, CreateSegmentationCommand> {

    private final ObjectMapper objectMapper;
    private final SegmentationRepository segmentationRepository;
    private final SegmentationFilterRepository segmentationFilterRepository;

    @Override
    public Segmentation handle(CreateSegmentationCommand cmd) {
        var segmentations = segmentationRepository.findByName(cmd.getName());
        if (segmentations != null && !segmentations.isEmpty())
            throw new BusinessException(String.format("Segmentation named %s already exists", cmd.getName()));

        var segmentation = Segmentation.builder().name(cmd.getName()).build();
        var filters = getFilters(cmd);

        if (filters.isEmpty())
            throw new BusinessException(String.format("Cannot get filter(s) for segmentation %s", cmd.getName()));

        segmentation.setSegmentationFilters(filters);

        return segmentationRepository.save(segmentation);
    }

    private List<SegmentationFilter> getFilters(CreateSegmentationCommand cmd) {
        if (cmd.getFilters() != null) {
            try {
                var json = objectMapper.writeValueAsString(cmd.getFilters());
                if (StringUtils.hasText(json)) return List.of(SegmentationFilter.builder().filters(json).build());

                throw new RuntimeException("segmentation filter(s) json is empty!");
            } catch (JsonProcessingException | RuntimeException e) {
                log.error("Cannot parse filter for segmentation {}. Trace {}", cmd.getName(), e.getMessage());
            }
        }

        if (cmd.getFilterIds() != null && !cmd.getFilterIds().isEmpty()) {
            return segmentationFilterRepository.findAllById(cmd.getFilterIds());
        }

        return Collections.emptyList();
    }
}
