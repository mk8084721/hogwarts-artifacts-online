package com.mfk.hogwarts_artifacts_online.wizard;

import com.mfk.hogwarts_artifacts_online.artifact.Artifact;
import com.mfk.hogwarts_artifacts_online.artifact.ArtifactRepository;
import com.mfk.hogwarts_artifacts_online.system.exception.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class WizardService {
    private final WizardRepository wizardRepository;
    private final ArtifactRepository artifactRepository;
    public List<Wizard> findAll() {
        return wizardRepository.findAll();
    }

    public Wizard findById(Integer id) {
        return wizardRepository.findById(id)
                .orElseThrow(()->new ObjectNotFoundException("wizard", id));
    }
    public Wizard save(Wizard newWizard) {
        return wizardRepository.save(newWizard);
    }
    public Wizard update(Integer wizardId, Wizard update){
        Wizard updatedWizard = findById(wizardId);
        updatedWizard.setName(update.getName());

        return wizardRepository.save(updatedWizard);
    }
    public void delete(Integer wizardId){
        Wizard wizard = findById(wizardId);
        wizard.removeAllArtifacts();
        wizardRepository.deleteById(wizardId);
    }

    public void assignArtifacts(Integer wizardId, String artifactId){
        Wizard wizard = wizardRepository.findById(wizardId)
                .orElseThrow(()->new ObjectNotFoundException("wizard",wizardId));

        Artifact artifact = artifactRepository.findById(artifactId)
                .orElseThrow(()->new ObjectNotFoundException("artifact",artifactId));
        // we need to see if the artifact is owned by a wizard
        if(artifact.getOwner() != null){
            artifact.getOwner().removeArtifact(artifact);
        }
        wizard.addArtifact(artifact);
    }
}
