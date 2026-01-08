package com.mfk.hogwarts_artifacts_online.artifact;

import com.mfk.hogwarts_artifacts_online.system.ApiResponse;
import com.mfk.hogwarts_artifacts_online.system.StatusCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ArtifactController {
    private final ArtifactService artifactService;

    @GetMapping("/api/v1/artifacts/{artifactId}")
    public ApiResponse findArtifactById(@PathVariable String artifactId){
        Artifact foundArtifact = artifactService.findById(artifactId);
        return new ApiResponse(
                true,
                StatusCode.SUCCESS,
                "Find One Success",
                foundArtifact
        );
    }

}
