package com.mfk.hogwarts_artifacts_online.artifact.dto;

import com.mfk.hogwarts_artifacts_online.wizard.dto.WizardDto;

public record ArtifactDto(String id,
                          String name,
                          String description,
                          String imageUrl,
                          WizardDto owner) {

}
