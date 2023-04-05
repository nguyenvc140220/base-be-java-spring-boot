package com.metechvn.contacts.commands;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import luongdev.cqrs.Request;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class DeleteSegmentationCommand implements Request<Boolean> {

    @NotNull
    private UUID id;

    private DeleteSegmentationCommand() {
    }

}
