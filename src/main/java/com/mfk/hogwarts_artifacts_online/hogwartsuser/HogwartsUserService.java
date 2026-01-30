package com.mfk.hogwarts_artifacts_online.hogwartsuser;

import com.mfk.hogwarts_artifacts_online.system.exception.ObjectNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class HogwartsUserService implements UserDetailsService {
    private final HogwartsUserRepository hogwartsUserRepository;
    private final PasswordEncoder passwordEncoder;

    public List<HogwartsUser> findAll() {
        return hogwartsUserRepository.findAll();
    }

    public HogwartsUser findById(Integer userId) {
        return hogwartsUserRepository.findById(userId)
                .orElseThrow(()->new ObjectNotFoundException("user",userId));
    }

    public HogwartsUser save(HogwartsUser newUser) {
        newUser.setPassword(this.passwordEncoder.encode(newUser.getPassword()));
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

    public void delete(Integer userId) {
        HogwartsUser foundedHogwartsUser = hogwartsUserRepository.findById(userId)
                .orElseThrow(()->new ObjectNotFoundException("user",userId));
        hogwartsUserRepository.delete(foundedHogwartsUser);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.hogwartsUserRepository.findByUsername(username)
                .map(MyUserPrincipal::new)
                .orElseThrow(()-> new UsernameNotFoundException("username "+ username+ " is not found"));
    }
}
