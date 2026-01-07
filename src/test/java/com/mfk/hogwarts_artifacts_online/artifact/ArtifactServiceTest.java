package com.mfk.hogwarts_artifacts_online.artifact;

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
    @InjectMocks
    ArtifactService artifactService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void TestFindByIdSucess() {
        // Given . Arrange inputs and targets , Define the behavior of Mock object artifactRepository
        /*
          "id": "1250808601744904191",
          "name": "Deluminator",
          "description": "A Deluminator is a device invented by Albus Dumbledore that resembles a cigarette lighter. It is used to remove or absorb (as well as return) the light from any light source to provide cover to the user.",
          "imageUrl": "ImageUrl",
          "owner": {
            "id": 1,
            "name": "Albus Dumbledore",
            "numberOfArtifacts": 2
          }
      */
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
}