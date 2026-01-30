package com.mfk.hogwarts_artifacts_online.hogwartsuser;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HogwartsUserRepository extends JpaRepository<HogwartsUser, Integer> {
    Optional<HogwartsUser> findByUsername(String username);
}
