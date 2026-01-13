package com.mfk.hogwarts_artifacts_online.wizard;

import com.mfk.hogwarts_artifacts_online.system.ApiResponse;
import com.mfk.hogwarts_artifacts_online.system.StatusCode;
import com.mfk.hogwarts_artifacts_online.wizard.converter.WizardToWizardDtoConverter;
import com.mfk.hogwarts_artifacts_online.wizard.dto.WizardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/wizards")
@RequiredArgsConstructor
public class WizardController {
    private final WizardService wizardService;
    private final WizardToWizardDtoConverter wizardToWizardDtoConverter;

    @GetMapping
    public ApiResponse findAllWizards(){
        List<Wizard> wizards =wizardService.findAll();
        List<WizardDto> wizardDtos = wizards.stream()
                .map(wizardToWizardDtoConverter::convert)
                .toList();
        return new ApiResponse(
                true,
                StatusCode.SUCCESS,
                "Find All Success",
                wizardDtos
        );
    }
    @GetMapping("/{wizardId}")
    public ApiResponse findWizardById(@PathVariable Integer wizardId){
        Wizard wizard = wizardService.findById(wizardId);
        WizardDto wizardDto = wizardToWizardDtoConverter.convert(wizard);
        return new ApiResponse(
                true,
                StatusCode.SUCCESS,
                "Find One Success",
                wizardDto
        );
    }
}
