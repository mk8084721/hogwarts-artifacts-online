package com.mfk.hogwarts_artifacts_online.wizard;

import com.mfk.hogwarts_artifacts_online.artifact.Artifact;
import com.mfk.hogwarts_artifacts_online.artifact.ArtifactRepository;
import com.mfk.hogwarts_artifacts_online.system.exception.ObjectNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class WizardServiceTest {
    @Mock
    WizardRepository wizardRepository;
    @Mock
    ArtifactRepository artifactRepository;
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
        assertThat(thrown).isInstanceOf(ObjectNotFoundException.class)
                .hasMessage("Could not find wizard with id 9 :(");
        verify(wizardRepository, times(1)).findById(9);
    }
    @Test
    void testSaveSuccess() {
        //Given
        given(wizardRepository.save(Mockito.any(Wizard.class)))
                .willReturn(wizards.get(0));

        //When
        Wizard savedWizard = wizardService.save(wizards.get(0));

        //Then
        assertThat(savedWizard.getId()).isEqualTo(1);
        assertThat(savedWizard.getName()).isEqualTo(wizards.get(0).getName());

        verify(wizardRepository, times(1)).save(wizards.get(0));

    }

    @Test
    void testUpdateSuccess() {
        //Given.
        given(wizardRepository.findById(wizards.get(0).getId()))
                .willReturn(Optional.of(wizards.get(0)));
        given(wizardRepository.save(Mockito.any(Wizard.class)))
                .willReturn(wizards.get(0));

        //When.
        Wizard result = wizardService.update(wizards.get(0).getId(), wizards.get(0));

        //Then.
        assertThat(result.getId()).isEqualTo(wizards.get(0).getId());
        assertThat(result.getName()).isEqualTo(wizards.get(0).getName());

        verify(wizardRepository, times(1)).findById(wizards.get(0).getId());
        verify(wizardRepository, times(1)).save(Mockito.any(Wizard.class));
    }
    @Test
    public void testDeleteSuccess(){
        //Given.
        given(wizardRepository.findById(wizards.get(0).getId()))
                .willReturn(Optional.of(wizards.get(0)));
        doNothing().when(wizardRepository).deleteById(eq(1));

        //When.
        wizardService.delete(1);
        //Then.
        verify(wizardRepository, times(1)).deleteById(1);
    }
    @Test
    public void deleteWizardErrorNotFound(){
        //Given.
        given(wizardRepository.findById(Mockito.any(Integer.class)))
                .willReturn(Optional.empty());
        //When.
        Throwable thrown = catchThrowable(()->{
            wizardService.delete(5);
        });
        //Then
        assertThat(thrown)
                .isInstanceOf(ObjectNotFoundException.class)
                .hasMessage("Could not find wizard with id 5 :(");
        verify(wizardRepository, times(1)).findById(5);
    }

    @Test
    public void testAssignArtifactSucess(){
        //Given.
        Artifact artifact = new Artifact();
        artifact.setId("1250808601744904191");
        artifact.setName("Deluminator");
        artifact.setDescription("A Deluminator is a device invented by Albus Dumbledore that resembles a cigarette lighter. It is used to remove or absorb (as well as return) the light from any light source to provide cover to the user.");
        artifact.setImageUrl("ImageUrl");

        wizards.get(0).addArtifact(artifact);

        given(this.artifactRepository.findById("1250808601744904191"))
                .willReturn(Optional.of(artifact));
        given(this.wizardRepository.findById(2))
                .willReturn(Optional.of(wizards.get(1)));

        //When
        this.wizardService.assignArtifacts(2, "1250808601744904191");

        //Then
        assertThat(artifact.getOwner().getId()).isEqualTo(2);
        assertThat(wizards.get(1).getArtifacts()).contains(artifact);
    }
    @Test
    public void testAssignArtifactErrorNonExsitenWizardId(){
        //Given.
        Artifact artifact = new Artifact();
        artifact.setId("1250808601744904191");
        artifact.setName("Deluminator");
        artifact.setDescription("A Deluminator is a device invented by Albus Dumbledore that resembles a cigarette lighter. It is used to remove or absorb (as well as return) the light from any light source to provide cover to the user.");
        artifact.setImageUrl("ImageUrl");

        given(wizardRepository.findById(Mockito.any(Integer.class)))
                .willReturn(Optional.empty());

        //When.
        Throwable thrown = catchThrowable(()->{
            wizardService.assignArtifacts(5,"1250808601744904191");
        });
        //Then
        assertThat(thrown)
                .isInstanceOf(ObjectNotFoundException.class)
                .hasMessage("Could not find wizard with id 5 :(");
    }
    @Test
    public void testAssignArtifactErrorNonExsitenArtifactId(){
        //Given.
        Artifact artifact = new Artifact();
        artifact.setId("1250808601744904191");
        artifact.setName("Deluminator");
        artifact.setDescription("A Deluminator is a device invented by Albus Dumbledore that resembles a cigarette lighter. It is used to remove or absorb (as well as return) the light from any light source to provide cover to the user.");
        artifact.setImageUrl("ImageUrl");

        given(artifactRepository.findById(Mockito.any(String.class)))
                .willReturn(Optional.empty());

        given(this.wizardRepository.findById(5))
                .willReturn(Optional.of(wizards.get(1)));


        //When.
        Throwable thrown = catchThrowable(()->{
            wizardService.assignArtifacts(5,"1250808601744904191");
        });
        //Then
        assertThat(thrown)
                .isInstanceOf(ObjectNotFoundException.class)
                .hasMessage("Could not find artifact with id 1250808601744904191 :(");
    }
}