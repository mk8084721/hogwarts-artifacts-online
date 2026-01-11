package com.mfk.hogwarts_artifacts_online.artifact;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mfk.hogwarts_artifacts_online.artifact.dto.ArtifactDto;
import com.mfk.hogwarts_artifacts_online.system.StatusCode;
import com.mfk.hogwarts_artifacts_online.wizard.Wizard;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
//@ActiveProfiles("test")
class ArtifactControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockitoBean
    ArtifactService artifactService;
    @Autowired
    ObjectMapper objectMapper;
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
    void testFindArtifactByIdSuccess() throws Exception {
        //Given.
        Artifact artifact = new Artifact();
        artifact.setId("1250808601744904191");
        artifact.setName("Deluminator");
        artifact.setDescription("A Deluminator is a device invented by Albus Dumbledore that resembles a cigarette lighter. It is used to remove or absorb (as well as return) the light from any light source to provide cover to the user.");
        artifact.setImageUrl("ImageUrl");

        Wizard wizard = new Wizard();
        wizard.setId(1);
        wizard.setName("Albus Dumbledore");
        artifact.setOwner(wizard);

        given(artifactService.findById("1250808601744904191"))
                .willReturn(artifact);

        //When and Then.
        this.mockMvc.perform(get("/api/v1/artifacts/1250808601744904191").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Find One Success"))
                .andExpect(jsonPath("$.data.id").value(artifact.getId()))
                .andExpect(jsonPath("$.data.description").value(artifact.getDescription()))
                .andExpect(jsonPath("$.data.imageUrl").value(artifact.getImageUrl()))
                .andExpect(jsonPath("$.data.name").value(artifact.getName()));

    }
    @Test
    void testFindArtifactByIdNotFound() throws Exception {
        //Given.
        given(artifactService.findById("anyId"))
                .willThrow(new ArtifactNotFoundException("anyId"));

        //When and Then.
        this.mockMvc.perform(get("/api/v1/artifacts/anyId").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find artifact with id anyId :("))
                .andExpect(jsonPath("$.data").isEmpty());

    }

    @Test
    void testFindAllArtifactsSuccess() throws Exception {
        //Given.
        given(artifactService.findAll())
                .willReturn(artifacts);
        //When and Then.
        this.mockMvc.perform(get("/api/v1/artifacts").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Find All Success"))
                .andExpect(jsonPath("$.data", Matchers.hasSize(artifacts.size())))
                .andExpect(jsonPath("$.data[0].id").value(artifacts.get(0).getId()))
                .andExpect(jsonPath("$.data[1].id").value(artifacts.get(1).getId()))
                .andExpect(jsonPath("$.data[2].id").value(artifacts.get(2).getId()));
    }

    @Test
    void testAddArtifactSuccess() throws Exception {
        //Given.
        ArtifactDto artifactDto = new ArtifactDto(
                null,
                "new",
                "newDesc",
                "newImage",
                null
        );
        String json = this.objectMapper.writeValueAsString(artifactDto);
        Artifact newArtifact = new Artifact("newArtifact", "newDescription","newImageUrl");
        newArtifact.setId("678910");
        given(artifactService.save(Mockito.any(Artifact.class))).willReturn(newArtifact);

        //When and Then.
        this.mockMvc.perform(post("/api/v1/artifacts").contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Add Success"))
                .andExpect(jsonPath("$.data.id").isNotEmpty())
                .andExpect(jsonPath("$.data.name").value(newArtifact.getName()))
                .andExpect(jsonPath("$.data.description").value(newArtifact.getDescription()))
                .andExpect(jsonPath("$.data.imageUrl").value(newArtifact.getImageUrl()));

    }

    @Test
    void testUpdateArtifactSuccess() throws Exception {
        //Given.
        ArtifactDto artifactDto = new ArtifactDto(
                null,
                "updatedName",
                "updatedDesc",
                "updatedImage",
                null
        );
        String json = this.objectMapper.writeValueAsString(artifactDto);
        Artifact updatedArtifact = new Artifact(
                "updatedName",
                "updatedDesc",
                "updatedImage");
        updatedArtifact.setId("678910");
        given(artifactService.update(
                eq("678910"),
                Mockito.any(Artifact.class))).willReturn(updatedArtifact);
        //When and Then.
        this.mockMvc.perform(put("/api/v1/artifacts/678910").contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Update Success"))
                .andExpect(jsonPath("$.data.id").value("678910"))
                .andExpect(jsonPath("$.data.name").value(updatedArtifact.getName()))
                .andExpect(jsonPath("$.data.description").value(updatedArtifact.getDescription()))
                .andExpect(jsonPath("$.data.imageUrl").value(updatedArtifact.getImageUrl()));
    }

    @Test
    void testUpdateArtifactErrorIdNotFound() throws Exception {
        //Given.
        ArtifactDto artifactDto = new ArtifactDto(
                null,
                "updatedName",
                "updatedDesc",
                "updatedImage",
                null
        );
        String json = this.objectMapper.writeValueAsString(artifactDto);
        given(artifactService.update(
                eq("678910"),
                Mockito.any(Artifact.class))).willThrow(new ArtifactNotFoundException("678910"));
        //When and Then.
        this.mockMvc.perform(put("/api/v1/artifacts/678910").contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find artifact with id 678910 :("))
                .andExpect(jsonPath("$.data").isEmpty());
    }
    @Test
    public void testDeleteArtifactSuccess() throws Exception {
        //Given
        doNothing().when(artifactService).delete(eq("678910"));


        this.mockMvc.perform(delete("/api/v1/artifacts/678910").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Delete Success"))
                .andExpect(jsonPath("$.data").isEmpty());
    }
    @Test
    public void testDeleteArtifactWithNonExistenId() throws Exception {
        //Given
        doThrow(new ArtifactNotFoundException("678910"))
                .when(artifactService).delete(eq("678910"));


        this.mockMvc.perform(delete("/api/v1/artifacts/678910").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find artifact with id 678910 :("))
                .andExpect(jsonPath("$.data").isEmpty());
    }
}