package com.mfk.hogwarts_artifacts_online.artifact;

import com.mfk.hogwarts_artifacts_online.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.transform.Result;

@RestController
@RequiredArgsConstructor
public class ArtifactController {
    private final ArtifactService artifactService;

    @GetMapping("/artifact/{artifactId}")
    public ApiResponse findArtifactById(@PathVariable String artifactId){
        return null;
    }

}
