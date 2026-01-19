package com.mfk.hogwarts_artifacts_online.wizard;

import com.mfk.hogwarts_artifacts_online.artifact.Artifact;
import com.mfk.hogwarts_artifacts_online.artifact.dto.ArtifactDto;
import com.mfk.hogwarts_artifacts_online.system.ApiResponse;
import com.mfk.hogwarts_artifacts_online.system.StatusCode;
import com.mfk.hogwarts_artifacts_online.wizard.converter.WizardDtoToWizardConverter;
import com.mfk.hogwarts_artifacts_online.wizard.converter.WizardToWizardDtoConverter;
import com.mfk.hogwarts_artifacts_online.wizard.dto.WizardDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.endpoint.base-url}/wizards")
@RequiredArgsConstructor
public class WizardController {
    private final WizardService wizardService;
    private final WizardToWizardDtoConverter wizardToWizardDtoConverter;
    private final WizardDtoToWizardConverter wizardDtoToWizardConverter;

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

    @PostMapping
    public ApiResponse addWizard(@Valid @RequestBody WizardDto wizardDto){

        Wizard newWizard = wizardDtoToWizardConverter.convert(wizardDto);
        Wizard savedWizard = wizardService.save(newWizard);
        WizardDto savedWizardDto = wizardToWizardDtoConverter.convert(savedWizard);

        return new ApiResponse(
                true,
                StatusCode.SUCCESS,
                "Add Success",
                savedWizardDto
        );
    }
    @PutMapping("/{wizardId}")
    public ApiResponse updateArtifact(@PathVariable Integer wizardId, @Valid @RequestBody WizardDto wizardDto){
        Wizard update = wizardDtoToWizardConverter.convert(wizardDto);
        Wizard updatedWizard = wizardService.update(wizardId, update);
        WizardDto updatedWizardDto = wizardToWizardDtoConverter.convert(updatedWizard);
        return new ApiResponse(
                true,
                StatusCode.SUCCESS,
                "Update Success",
                updatedWizardDto
        );
    }
    @DeleteMapping("/{wizardId}")
    public ApiResponse deleteWizard(@PathVariable Integer wizardId){
        wizardService.delete(wizardId);
        return new ApiResponse(
                true,
                StatusCode.SUCCESS,
                "Delete Success"
        );
    }
    @PutMapping("/{wizardId}/artifacts/{artifactId}")
    public ApiResponse assignArtifact(@PathVariable Integer wizardId, @PathVariable String artifactId){
        wizardService.assignArtifacts(wizardId, artifactId);
        return new ApiResponse(
                true,
                StatusCode.SUCCESS,
                "Artifact Assignment Success"
        );
    }
}
