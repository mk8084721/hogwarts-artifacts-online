package com.mfk.hogwarts_artifacts_online.wizard;

import com.mfk.hogwarts_artifacts_online.artifact.ArtifactNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class WizardServiceTest {
    @Mock
    WizardRepository wizardRepository;
    @InjectMocks
    WizardService wizardService;
    List<Wizard> wizards = new ArrayList<>();

    @BeforeEach
    void setUp() {
        Wizard dumbledore = new Wizard();
        dumbledore.setId(1);
        dumbledore.setName("Albus Dumbledore");

        Wizard harry = new Wizard();
        harry.setId(2);
        harry.setName("Harry Potter");

        Wizard neville = new Wizard();
        neville.setId(3);
        neville.setName("Neville Longbottom");

        wizards.add(dumbledore);
        wizards.add(harry);
        wizards.add(neville);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testFindAllSuccess() {
        //Given.
        given(wizardRepository.findAll()).willReturn(wizards);
        //When.
        List<Wizard> returnedWizardList = wizardService.findAll();
        //Then.
        assertThat(returnedWizardList.size()).isEqualTo(3);
        assertThat(returnedWizardList.get(0).getId()).isEqualTo(1);
        assertThat(returnedWizardList.get(0).getName()).isEqualTo(wizards.get(0).getName());
        assertThat(returnedWizardList.get(1).getId()).isEqualTo(2);
        assertThat(returnedWizardList.get(1).getName()).isEqualTo(wizards.get(1).getName());
        assertThat(returnedWizardList.get(2).getId()).isEqualTo(3);
        assertThat(returnedWizardList.get(2).getName()).isEqualTo(wizards.get(2).getName());

        verify(wizardRepository, times(1)).findAll();
    }
    @Test
    void testFindByIdSuccess() {
        //Given.
        given(wizardRepository.findById(1)).willReturn(Optional.of(wizards.get(0)));
        //When.
        Wizard returnedWizard = wizardService.findById(1);
        //Then.
        assertThat(returnedWizard.getId()).isEqualTo(1);
        assertThat(returnedWizard.getName()).isEqualTo(wizards.get(0).getName());
        verify(wizardRepository, times(1)).findById(1);
    }
    @Test
    void testFindByIdErrorNotFound() {
        //Given.
        given(wizardRepository.findById(9)).willReturn(Optional.empty());
        //When.
        Throwable thrown = catchThrowable(()->{
            Wizard returnedWizard = wizardService.findById(9);
        });
        //Then.
        assertThat(thrown).isInstanceOf(WizardNotFoundException.class)
                .hasMessage("Could not find artifact with Id 9 :(");
        verify(wizardRepository, times(1)).findById(9);
    }
}