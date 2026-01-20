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
}
