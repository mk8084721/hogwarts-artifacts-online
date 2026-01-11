package com.mfk.hogwarts_artifacts_online.wizard.converter;

import com.mfk.hogwarts_artifacts_online.wizard.Wizard;
import com.mfk.hogwarts_artifacts_online.wizard.dto.WizardDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class WizardToWizardDtoConverter implements Converter<Wizard, WizardDto> {

    @Override
    public WizardDto convert(Wizard source) {
        return new WizardDto(
                source.getId(),
                source.getName(),
                source.getNumberOfArtifacts()
        );
    }
}
