package com.mfk.hogwarts_artifacts_online.artifact.converter;

import com.mfk.hogwarts_artifacts_online.artifact.Artifact;
import com.mfk.hogwarts_artifacts_online.artifact.dto.ArtifactDto;
import com.mfk.hogwarts_artifacts_online.wizard.converter.WizardToWizardDtoConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ArtifactDtoToArtifactConverter implements Converter<ArtifactDto, Artifact> {
    @Override
    public Artifact convert(ArtifactDto source) {
        Artifact artifact = new Artifact(
                source.name(),
                source.description(),
                source.imageUrl()
        );
        artifact.setId(source.id());
        return artifact;
    }
}
