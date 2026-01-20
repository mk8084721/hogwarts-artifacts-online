package com.mfk.hogwarts_artifacts_online.hogwartsuser.converter;

import com.mfk.hogwarts_artifacts_online.hogwartsuser.HogwartsUser;
import com.mfk.hogwarts_artifacts_online.hogwartsuser.dto.HogwartsUserRequest;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class HogwartsUserRequestToHogwartsUserConverter implements Converter<HogwartsUserRequest, HogwartsUser> {
    @Override
    public HogwartsUser convert(HogwartsUserRequest source) {
        HogwartsUser hogwartsUser = new HogwartsUser();
        hogwartsUser.setUsername(source.username());
        hogwartsUser.setPassword(source.password());
        hogwartsUser.setEnabled(source.enabled());
        hogwartsUser.setRoles(source.roles());
        return hogwartsUser;
    }
}
