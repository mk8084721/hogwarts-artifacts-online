package com.mfk.hogwarts_artifacts_online.artifact;

import com.mfk.hogwarts_artifacts_online.artifact.utils.IdWorker;
import com.mfk.hogwarts_artifacts_online.wizard.Wizard;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ArtifactServiceTest {
    @Mock
    ArtifactRepository artifactRepository;
    @Mock
    IdWorker idWorker;
    @InjectMocks
    ArtifactService artifactService;
    List<Artifact> artifacts;

    @BeforeEach
    void setUp() {
        this.artifacts = new ArrayList<>();

        Artifact a1 = new Artifact();
        a1.setId("1");
        a1.setName("Deluminator 1");
        a1.setDescription("Description 1");
        a1.setImageUrl("ImageUrl 1");
        this.artifacts.add(a1);

        Artifact a2 = new Artifact();
        a2.setId("2");
        a2.setName("Deluminator 2");
        a2.setDescription("Description 2");
        a2.setImageUrl("ImageUrl 2");
        this.artifacts.add(a2);

        Artifact a3 = new Artifact();
        a3.setId("3");
        a3.setName("Deluminator 3");
        a3.setDescription("Description 3");
        a3.setImageUrl("ImageUrl 3");
        this.artifacts.add(a3);

        Artifact a4 = new Artifact();
        a4.setId("4");
        a4.setName("Deluminator 4");
        a4.setDescription("Description 4");
        a4.setImageUrl("ImageUrl 4");
        this.artifacts.add(a4);

        Artifact a5 = new Artifact();
        a5.setId("5");
        a5.setName("Deluminator 5");
        a5.setDescription("Description 5");
        a5.setImageUrl("ImageUrl 5");
        this.artifacts.add(a5);

        Artifact a6 = new Artifact();
        a6.setId("6");
        a6.setName("Deluminator 6");
        a6.setDescription("Description 6");
        a6.setImageUrl("ImageUrl 6");
        this.artifacts.add(a6);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void TestFindByIdSuccess() {
        Artifact artifact = new Artifact();
        artifact.setId("1250808601744904191");
        artifact.setName("Deluminator");
        artifact.setDescription("A Deluminator is a device invented by Albus Dumbledore that resembles a cigarette lighter. It is used to remove or absorb (as well as return) the light from any light source to provide cover to the user.");
        artifact.setImageUrl("ImageUrl");

        Wizard wizard = new Wizard();
        wizard.setId(1);
        wizard.setName("Albus Dumbledore");
        artifact.setOwner(wizard);

        given(artifactRepository.findById("1250808601744904191")).willReturn(Optional.of(artifact)); //Defines the behavior of the mock object

        // When . Act on the target behaviour , When steps should cover the method to be tested.
        Artifact returnedArtifact = artifactService.findById("1250808601744904191");

        // Then . Assert expected outcomes.
        assertThat(returnedArtifact.getId()).isEqualTo(artifact.getId());
        assertThat(returnedArtifact.getName()).isEqualTo(artifact.getName());
        assertThat(returnedArtifact.getDescription()).isEqualTo(artifact.getDescription());
        assertThat(returnedArtifact.getImageUrl()).isEqualTo(artifact.getImageUrl());

        verify(artifactRepository, times(1)).findById("1250808601744904191");

    }

    @Test
    void testFindByIdNotFound(){
        //Given .
        given(artifactRepository.findById(Mockito.any(String.class))).willReturn(Optional.empty());
        //When.
        Throwable thrown = catchThrowable(()->{
                    Artifact returnedArtifact = artifactService.findById("anyId");
                });
        //Then
        assertThat(thrown)
                .isInstanceOf(ArtifactNotFoundException.class)
                .hasMessage("Could not find artifact with id anyId :(");
        verify(artifactRepository, times(1)).findById("anyId");
    }

    @Test
    void testFindAllSucess() {
        //Given
        given(artifactRepository.findAll()).willReturn(artifacts);
        //When
        List<Artifact> returnedArtifacts = artifactRepository.findAll();
        //Then
        assertThat(returnedArtifacts.size()).isEqualTo(artifacts.size());
        verify(artifactRepository, times(1)).findAll();
    }

    @Test
    void testSaveSucess() {
        //Given
        Artifact newArtifact = new Artifact("new name", "new desc", "newImage");
        given(idWorker.nextId()).willReturn(123456L);
        given(artifactRepository.save(newArtifact)).willReturn(newArtifact);

        //When
        Artifact savedArtifact = artifactService.save(newArtifact);

        //Then
        assertThat(savedArtifact.getId()).isEqualTo("123456");
        assertThat(savedArtifact.getName()).isEqualTo(newArtifact.getName());
        assertThat(savedArtifact.getDescription()).isEqualTo(newArtifact.getDescription());
        assertThat(savedArtifact.getImageUrl()).isEqualTo(newArtifact.getImageUrl());

        verify(artifactRepository, times(1)).save(newArtifact);

    }

    @Test
    void testUpdateSuccess() {
        //Given.
        Artifact oldArtifact = new Artifact();
        oldArtifact.setId("1250808601744904191");
        oldArtifact.setName("Deluminator");
        oldArtifact.setDescription("A Deluminator is a device invented by Albus Dumbledore that resembles a cigarette lighter. It is used to remove or absorb (as well as return) the light from any light source to provide cover to the user.");
        oldArtifact.setImageUrl("ImageUrl");
        Artifact updatedArtifact = new Artifact(
                "magic seif",
                "a magic seif can appear when you want it bad",
                "image/imageurl"
        );
        updatedArtifact.setId(oldArtifact.getId());

        given(artifactRepository.findById(oldArtifact.getId())).willReturn(Optional.of(oldArtifact));
        given(artifactRepository.save(Mockito.any(Artifact.class))).willReturn(updatedArtifact);

        //When.
        Artifact result = artifactService.update(oldArtifact.getId(), updatedArtifact);

        //Then.
        assertThat(result.getId()).isEqualTo(oldArtifact.getId());
        assertThat(result.getName()).isEqualTo(updatedArtifact.getName());
        assertThat(result.getDescription()).isEqualTo(updatedArtifact.getDescription());
        assertThat(result.getImageUrl()).isEqualTo(updatedArtifact.getImageUrl());

        verify(artifactRepository, times(1)).findById(oldArtifact.getId());
        verify(artifactRepository, times(1)).save(Mockito.any(Artifact.class));
    }
}