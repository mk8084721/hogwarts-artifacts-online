package com.mfk.hogwarts_artifacts_online.wizard.dto;

import jakarta.validation.constraints.NotEmpty;

public record WizardDto(Integer id,
                        @NotEmpty
                        String name,
                        Integer numberOfArtifacts) {
}
