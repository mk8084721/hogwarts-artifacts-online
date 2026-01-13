package com.mfk.hogwarts_artifacts_online.wizard;

import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class WizardService {
    private WizardRepository wizardRepository;
    public List<Wizard> findAll() {
        return wizardRepository.findAll();
    }

    public Wizard findById(Integer id) {
        return wizardRepository.findById(id)
                .orElseThrow(()->new WizardNotFoundException(id));
    }
}
