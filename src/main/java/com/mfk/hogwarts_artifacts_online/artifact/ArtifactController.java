package com.mfk.hogwarts_artifacts_online.artifact;

import com.mfk.hogwarts_artifacts_online.artifact.converter.ArtifactDtoToArtifactConverter;
import com.mfk.hogwarts_artifacts_online.artifact.dto.ArtifactDto;
import com.mfk.hogwarts_artifacts_online.artifact.converter.ArtifactToArtifactDtoConverter;
import com.mfk.hogwarts_artifacts_online.system.ApiResponse;
import com.mfk.hogwarts_artifacts_online.system.StatusCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ArtifactController {
    private final ArtifactService artifactService;
    private final ArtifactToArtifactDtoConverter artifactToArtifactDtoConverter;
    private final ArtifactDtoToArtifactConverter artifactDtoToArtifactConverter;

    @GetMapping("/api/v1/artifacts/{artifactId}")
    public ApiResponse findArtifactById(@PathVariable String artifactId){
        Artifact foundArtifact = artifactService.findById(artifactId);
        ArtifactDto artifactDto= artifactToArtifactDtoConverter.convert(foundArtifact);
        return new ApiResponse(
                true,
                StatusCode.SUCCESS,
                "Find One Success",
                artifactDto
        );
    }
    @GetMapping("/api/v1/artifacts")
    public ApiResponse findAllArtifacts(){
        List<Artifact> allArtifacts = artifactService.findAll();
        List<ArtifactDto> AllartifactDtos = allArtifacts.stream()
                .map(artifactToArtifactDtoConverter::convert)
                .toList();
        return new ApiResponse(
                true,
                StatusCode.SUCCESS,
                "Find All Success",
                AllartifactDtos
        );
    }
    @PostMapping("/api/v1/artifacts")
    public ApiResponse addArtifact(@Valid @RequestBody ArtifactDto artifactDto){

        Artifact newArtifact = artifactDtoToArtifactConverter.convert(artifactDto);
        Artifact savedArtifact = artifactService.save(newArtifact);
        ArtifactDto savedArtifactDto = artifactToArtifactDtoConverter.convert(savedArtifact);

        return new ApiResponse(
                true,
                StatusCode.SUCCESS,
                "Add Success",
                savedArtifactDto
        );
    }


}
