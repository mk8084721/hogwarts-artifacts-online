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
    public Artifact update(String artifactId, Artifact update){
        Artifact updatedArtifact = findById(artifactId);
        updatedArtifact.setName(update.getName());
        updatedArtifact.setDescription(update.getDescription());
        updatedArtifact.setImageUrl(update.getImageUrl());

        return artifactRepository.save(updatedArtifact);
    }
    public void delete(String artifactId){
        Artifact artifact = findById(artifactId);
        artifactRepository.deleteById(artifactId);
    }
}
