package com.metechvn.contacts.commands;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.metechvn.contacts.entities.Segmentation;
import com.metechvn.filter.Expression;
import lombok.Builder;
import lombok.Data;
import luongdev.cqrs.Request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Data
public class CreateSegmentationCommand implements Request<Segmentation> {

    @NotNull
    @NotEmpty
    private String name;
    private Expression filters;
    private List<UUID> filterIds;

    @Builder
    @JsonCreator
    public CreateSegmentationCommand(String name, Expression filters, List<UUID> filterIds) {
        this.name = name;
        this.filters = filters;
        this.filterIds = filterIds;
    }
}
