package com.mfk.hogwarts_artifacts_online.wizard;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mfk.hogwarts_artifacts_online.artifact.ArtifactService;
import com.mfk.hogwarts_artifacts_online.system.StatusCode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@AutoConfigureMockMvc
class WizardControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockitoBean
    WizardService wizardService;
    @Autowired
    ObjectMapper objectMapper;
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
    void testFindAllWizardsSuccess() throws Exception {
        //Given
        given(wizardService.findAll()).willReturn(wizards);

        //When & Then
        mockMvc.perform(get("/api/v1/wizards").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Find All Success"))
                .andExpect(jsonPath("$.data.size()").value(wizards.size()));
    }
    @Test
    void testFindWizardByIdSuccess() throws Exception {
        //Given
        given(wizardService.findById(Mockito.any(Integer.class))).willReturn(wizards.get(0));

        //When & Then
        mockMvc.perform(get("/api/v1/wizards/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Find One Success"))
                .andExpect(jsonPath("$.data.id").value(wizards.get(0).getId()))
                .andExpect(jsonPath("$.data.name").value(wizards.get(0).getName()));
    }
    @Test
    void testFindWizardByIdErrorNotFound() throws Exception {
        //Given
        given(wizardService.findById(Mockito.any(Integer.class)))
                .willThrow(new WizardNotFoundException(1));

        //When & Then
        mockMvc.perform(get("/api/v1/wizards/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find artifact with Id 1 :("))
                .andExpect(jsonPath("$.data").isEmpty());
    }
}