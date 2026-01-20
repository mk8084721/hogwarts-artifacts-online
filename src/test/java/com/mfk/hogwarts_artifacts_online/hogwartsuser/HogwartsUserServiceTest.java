package com.mfk.hogwarts_artifacts_online.hogwartsuser;

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

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class HogwartsUserServiceTest {
    @Mock
    HogwartsUserRepository hogwartsUserRepository;
    @InjectMocks
    HogwartsUserService hogwartsUserService;

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
    void testFindAllSuccess() {
        //Given
        given(this.hogwartsUserRepository.findAll()).willReturn(users);
        //When
        List<HogwartsUser> returnedHogwartsUserList = this.hogwartsUserService.findAll();
        //Then
        assertThat(returnedHogwartsUserList.get(0).getUsername())
                .isEqualTo(users.get(0).getUsername());
        assertThat(returnedHogwartsUserList.size())
                .isEqualTo(users.size());

        verify(hogwartsUserRepository,times(1)).findAll();
    }

    @Test
    void testFindByIdSuccess() {
        //Given
        given(this.hogwartsUserRepository.findById(1)).willReturn(Optional.of(users.get(0)));
        //When
        HogwartsUser returnedHogwartsUser = this.hogwartsUserService.findById(1);
        //Then
        assertThat(returnedHogwartsUser.getUsername())
                .isEqualTo(users.get(0).getUsername());
        assertThat(returnedHogwartsUser.getId())
                .isEqualTo(users.get(0).getId());
        assertThat(returnedHogwartsUser.getRoles())
                .isEqualTo(users.get(0).getRoles());
        assertThat(returnedHogwartsUser.isEnabled())
                .isEqualTo(users.get(0).isEnabled());

        verify(hogwartsUserRepository,times(1)).findById(1);
    }
    @Test
    void testFindByIdErrorUserNotFound() {
        //Given
        given(this.hogwartsUserRepository.findById(Mockito.any(Integer.class)))
                .willReturn(Optional.empty());
        //When
        Throwable thrown = catchThrowable(()-> {
                    this.hogwartsUserService.findById(1);
                });
        //Then
        assertThat(thrown)
                .isInstanceOf(ObjectNotFoundException.class)
                .hasMessage("Could not find user with id 1 :(");
        verify(hogwartsUserRepository, times(1)).findById(1);
    }
    @Test
    void testSaveSuccess() {
        //Given
        given(this.hogwartsUserRepository.save(Mockito.any(HogwartsUser.class)))
                .willReturn(users.get(0));
        //When
        HogwartsUser savedHogwartsUser = this.hogwartsUserService.save(users.get(0));
        //Then
        assertThat(savedHogwartsUser.getId())
                .isEqualTo(1);
        assertThat(savedHogwartsUser.getUsername())
                .isEqualTo(users.get(0).getUsername());
        assertThat(savedHogwartsUser.getRoles())
                .isEqualTo(users.get(0).getRoles());
        assertThat(savedHogwartsUser.isEnabled())
                .isEqualTo(users.get(0).isEnabled());

        verify(hogwartsUserRepository,times(1)).save(Mockito.any(HogwartsUser.class));
    }
    @Test
    void testUpdateByIdSuccess() {
        //Given
        HogwartsUser update = new HogwartsUser();
        update.setId(2);
        update.setUsername("newUsername");
        update.setEnabled(false);
        update.setRoles("new_Role");
        given(this.hogwartsUserRepository.findById(2))
                .willReturn(Optional.of(users.get(1)));
        given(this.hogwartsUserRepository.save(users.get(1)))
                .willReturn(update);
        //When
        HogwartsUser updatedHogwartsUser = this.hogwartsUserService.updateById(2,users.get(1));
        //Then
        assertThat(updatedHogwartsUser.getId())
                .isEqualTo(2);
        assertThat(updatedHogwartsUser.getUsername())
                .isEqualTo(update.getUsername());
        assertThat(updatedHogwartsUser.getRoles())
                .isEqualTo(update.getRoles());
        assertThat(updatedHogwartsUser.isEnabled())
                .isEqualTo(update.isEnabled());

        verify(hogwartsUserRepository,times(1)).save(users.get(1));
        verify(hogwartsUserRepository,times(1)).findById(2);
    }
    @Test
    void testUpdateByIdErrorUserNotFound() {
        //Given
        given(this.hogwartsUserRepository.findById(Mockito.any(Integer.class)))
                .willReturn(Optional.empty());
        //When
        Throwable thrown = catchThrowable(()-> {
            this.hogwartsUserService.updateById(1 , users.get(0));
        });
        //Then
        assertThat(thrown)
                .isInstanceOf(ObjectNotFoundException.class)
                .hasMessage("Could not find user with id 1 :(");
        verify(hogwartsUserRepository, times(1)).findById(1);
    }

}