package com.mfk.hogwarts_artifacts_online.hogwartsuser;

import com.mfk.hogwarts_artifacts_online.system.exception.ObjectNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class HogwartsUserService {
    private final HogwartsUserRepository hogwartsUserRepository;

    public List<HogwartsUser> findAll() {
        return hogwartsUserRepository.findAll();
    }

    public HogwartsUser findById(Integer userId) {
        return hogwartsUserRepository.findById(userId)
                .orElseThrow(()->new ObjectNotFoundException("user",userId));
    }

    public HogwartsUser save(HogwartsUser newUser) {
        return hogwartsUserRepository.save(newUser);
    }

    public HogwartsUser updateById(Integer userId, HogwartsUser update) {
        HogwartsUser foundedHogwartsUser = hogwartsUserRepository.findById(userId)
                .orElseThrow(()->new ObjectNotFoundException("user",userId));

        foundedHogwartsUser.setId(userId);
        foundedHogwartsUser.setUsername(update.getUsername());
        foundedHogwartsUser.setEnabled(update.isEnabled());
        foundedHogwartsUser.setRoles(update.getRoles());

        return hogwartsUserRepository.save(foundedHogwartsUser);
    }
}
