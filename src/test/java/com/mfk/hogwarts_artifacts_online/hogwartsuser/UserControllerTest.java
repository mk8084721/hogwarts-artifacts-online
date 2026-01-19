package com.mfk.hogwarts_artifacts_online.hogwartsuser;

import com.mfk.hogwarts_artifacts_online.system.StatusCode;
import com.mfk.hogwarts_artifacts_online.system.exception.ObjectNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockitoBean
    HogwartsUserService hogwartsUserService;
    @Value("${api.endpoint.base-url}")
    String baseUrl;
    List<HogwartsUser> users;

    @BeforeEach
    void setUp() {
        this.users = new ArrayList<>();

        HogwartsUser user1 = new HogwartsUser();
        user1.setId(1);
        user1.setUsername("john");
        user1.setEnabled(true);
        user1.setRoles("admin user");

        HogwartsUser user2 = new HogwartsUser();
        user2.setId(2);
        user2.setUsername("eric");
        user2.setEnabled(true);
        user2.setRoles("user");

        HogwartsUser user3 = new HogwartsUser();
        user3.setId(3);
        user3.setUsername("tom");
        user3.setEnabled(false);
        user3.setRoles("user");

        users.add(user1);
        users.add(user2);
        users.add(user3);

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testFindAllUsersSuccess() throws Exception {
        given(hogwartsUserService.findAll())
                .willReturn(users);

        this.mockMvc.perform(get(baseUrl+"/users").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Find All Success"))
                .andExpect(jsonPath("$.data.size()").value(users.size()));

    }
    @Test
    void testFindUserByIdSuccess() throws Exception {
        //Given
        given(hogwartsUserService.findById(1))
                .willReturn(users.get(0));
        //When & Then
        this.mockMvc.perform(get(baseUrl+"/users/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Find One Success"))
                .andExpect(jsonPath("$.data.id").value(users.get(0).getId()))
                .andExpect(jsonPath("$.data.username").value(users.get(0).getUsername()))
                .andExpect(jsonPath("$.data.enabled").value(users.get(0).isEnabled()))
                .andExpect(jsonPath("$.data.roles").value(users.get(0).getRoles()));
    }
    @Test
    void testFindUserByIdErrorUserNotFound() throws Exception {
        //Given
        given(hogwartsUserService.findById(1))
                .willThrow(new ObjectNotFoundException("user",1));
        //When & Then
        this.mockMvc.perform(get(baseUrl+"/users/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find user with id 1 :("))
                .andExpect(jsonPath("$.data").isEmpty());
    }
}