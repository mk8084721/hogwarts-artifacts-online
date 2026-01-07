package com.mfk.hogwarts_artifacts_online.artifact;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class ArtifactService {
    private final ArtifactRepository artifactRepository;

    public Artifact findById(String artifactId){

        return artifactRepository.findById(artifactId)
                .orElseThrow(()-> new ArtifactNotFoundException(artifactId));
    }
}
