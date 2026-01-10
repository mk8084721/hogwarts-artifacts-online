package com.mfk.hogwarts_artifacts_online.artifact;

import com.mfk.hogwarts_artifacts_online.artifact.utils.IdWorker;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ArtifactService {
    private final ArtifactRepository artifactRepository;
    private final IdWorker idWorker;

    public Artifact findById(String artifactId){
        return artifactRepository.findById(artifactId)
                .orElseThrow(()-> new ArtifactNotFoundException(artifactId));
    }
    public List<Artifact> findAll(){
        return artifactRepository.findAll();
    }

    public Artifact save(Artifact newArtifact) {
        newArtifact.setId(idWorker.nextId() +"");
        return artifactRepository.save(newArtifact);
    }
}
