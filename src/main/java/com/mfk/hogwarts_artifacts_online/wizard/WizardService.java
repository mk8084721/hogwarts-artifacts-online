package com.mfk.hogwarts_artifacts_online.wizard;

import com.mfk.hogwarts_artifacts_online.artifact.Artifact;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class WizardService {
    private final WizardRepository wizardRepository;
    public List<Wizard> findAll() {
        return wizardRepository.findAll();
    }

    public Wizard findById(Integer id) {
        return wizardRepository.findById(id)
                .orElseThrow(()->new WizardNotFoundException(id));
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
        wizardRepository.deleteById(wizardId);
    }
}
