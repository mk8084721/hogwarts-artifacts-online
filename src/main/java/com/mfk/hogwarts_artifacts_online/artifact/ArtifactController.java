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
@RequestMapping("/api/v1/artifacts")
@RequiredArgsConstructor
public class ArtifactController {
    private final ArtifactService artifactService;
    private final ArtifactToArtifactDtoConverter artifactToArtifactDtoConverter;
    private final ArtifactDtoToArtifactConverter artifactDtoToArtifactConverter;

    @GetMapping("/{artifactId}")
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
    @GetMapping
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
    @PostMapping
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
    @PutMapping("/{artifactId}")
    public ApiResponse updateArtifact(@PathVariable String artifactId, @Valid @RequestBody ArtifactDto artifactDto){
        Artifact update = artifactDtoToArtifactConverter.convert(artifactDto);
        Artifact updatedArtifact = artifactService.update(artifactId, update);
        ArtifactDto updatedArtifactDto = artifactToArtifactDtoConverter.convert(updatedArtifact);
        return new ApiResponse(
                true,
                StatusCode.SUCCESS,
                "Update Success",
                updatedArtifactDto
        );
    }
    @DeleteMapping("/{artifactId}")
    public ApiResponse deleteArtifact(@PathVariable String artifactId){
        artifactService.delete(artifactId);
        return new ApiResponse(
                true,
                StatusCode.SUCCESS,
                "Delete Success"
        );
    }


}
