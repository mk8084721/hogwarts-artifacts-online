package com.mfk.hogwarts_artifacts_online.security;

import com.mfk.hogwarts_artifacts_online.hogwartsuser.HogwartsUser;
import com.mfk.hogwarts_artifacts_online.hogwartsuser.MyUserPrincipal;
import com.mfk.hogwarts_artifacts_online.hogwartsuser.converter.HogwartsUserToHogwartsUserDtoConverter;
import com.mfk.hogwarts_artifacts_online.hogwartsuser.dto.HogwartsUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtProvider jwtProvider;
    private final HogwartsUserToHogwartsUserDtoConverter userToUserDtoConverter;

    public Map<String, Object> createLoginInfo(Authentication authentication) {
        //create user info .
        MyUserPrincipal principal = (MyUserPrincipal) authentication.getPrincipal();
        HogwartsUser hogwartsUser = principal.getHogwartsUser();
        HogwartsUserDto userDto = this.userToUserDtoConverter.convert(hogwartsUser);

        //then create jwt.
        String token = this.jwtProvider.createToken(authentication);
        Map<String, Object> loginResultMap = new HashMap<>();

        loginResultMap.put("userInfo", userDto);
        loginResultMap.put("token", token);

        return loginResultMap;
    }
}
