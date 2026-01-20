package com.mfk.hogwarts_artifacts_online.hogwartsuser.converter;

import com.mfk.hogwarts_artifacts_online.hogwartsuser.HogwartsUser;
import com.mfk.hogwarts_artifacts_online.hogwartsuser.dto.HogwartsUserDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class HogwartsUserDtoToHogwartsUserConverter implements Converter<HogwartsUserDto , HogwartsUser> {
    @Override
    public HogwartsUser convert(HogwartsUserDto source) {
        HogwartsUser hogwartsUser = new HogwartsUser();
        hogwartsUser.setUsername(source.username());
        hogwartsUser.setEnabled(source.enabled());
        hogwartsUser.setRoles(source.roles());

        return hogwartsUser;
    }
}
