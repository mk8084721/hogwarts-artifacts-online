package com.mfk.hogwarts_artifacts_online.hogwartsuser.converter;

import com.mfk.hogwarts_artifacts_online.hogwartsuser.HogwartsUser;
import com.mfk.hogwarts_artifacts_online.hogwartsuser.dto.HogwartsUserDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class HogwartsUserToHogwartsUserDtoConverter implements Converter<HogwartsUser , HogwartsUserDto> {
    @Override
    public HogwartsUserDto convert(HogwartsUser source) {
        HogwartsUserDto userDto = new HogwartsUserDto(
                source.getId(),
                source.getUsername(),
                source.isEnabled(),
                source.getRoles()
        );
        return userDto;
    }
}
