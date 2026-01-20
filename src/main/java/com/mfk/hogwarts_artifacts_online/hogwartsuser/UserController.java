package com.mfk.hogwarts_artifacts_online.hogwartsuser;

import com.mfk.hogwarts_artifacts_online.hogwartsuser.converter.HogwartsUserDtoToHogwartsUserConverter;
import com.mfk.hogwarts_artifacts_online.hogwartsuser.converter.HogwartsUserRequestToHogwartsUserConverter;
import com.mfk.hogwarts_artifacts_online.hogwartsuser.converter.HogwartsUserToHogwartsUserDtoConverter;
import com.mfk.hogwarts_artifacts_online.hogwartsuser.dto.HogwartsUserDto;
import com.mfk.hogwarts_artifacts_online.hogwartsuser.dto.HogwartsUserRequest;
import com.mfk.hogwarts_artifacts_online.system.ApiResponse;
import com.mfk.hogwarts_artifacts_online.system.StatusCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.endpoint.base-url}/users")
@RequiredArgsConstructor
public class UserController {
    private final HogwartsUserService hogwartsUserService;
    private final HogwartsUserToHogwartsUserDtoConverter hogwartsUserToHogwartsUserDtoConverter;
    private final HogwartsUserDtoToHogwartsUserConverter hogwartsUserDtoToHogwartsUserConverter;
    private final HogwartsUserRequestToHogwartsUserConverter hogwartsUserRequestToHogwartsUserConverter;

    @GetMapping
    public ApiResponse findAllUsers(){
        List<HogwartsUser> hogwartsUsers = hogwartsUserService.findAll();
        List<HogwartsUserDto> hogwartsUserDtos = hogwartsUsers.stream()
                .map(hogwartsUserToHogwartsUserDtoConverter::convert)
                .toList();
        return new ApiResponse(
                true,
                StatusCode.SUCCESS,
                "Find All Success",
                hogwartsUserDtos
        );
    }
    @GetMapping("/{userId}")
    public ApiResponse findUserById(@PathVariable Integer userId){
        HogwartsUser hogwartsUser = hogwartsUserService.findById(userId);
        HogwartsUserDto hogwartsUserDto = hogwartsUserToHogwartsUserDtoConverter.convert(hogwartsUser);
        return new ApiResponse(
                true,
                StatusCode.SUCCESS,
                "Find One Success",
                hogwartsUserDto
        );
    }

    @PostMapping
    public ApiResponse saveUser(@RequestBody @Valid HogwartsUserRequest request){
        HogwartsUser requestHogwartsUser = hogwartsUserRequestToHogwartsUserConverter.convert(request);
        HogwartsUser savedHogwartsUser = hogwartsUserService.save(requestHogwartsUser);
        HogwartsUserDto hogwartsUserDto = hogwartsUserToHogwartsUserDtoConverter
                .convert(savedHogwartsUser);
        return new ApiResponse(
                true,
                StatusCode.SUCCESS,
                "Add Success",
                hogwartsUserDto
        );
    }

    @PutMapping("/{userId}")
    public ApiResponse updateUserById(@PathVariable Integer userId,@RequestBody @Valid HogwartsUserDto update){
        HogwartsUser hogwartsUserUpdate = hogwartsUserDtoToHogwartsUserConverter.convert(update);
        HogwartsUser updatedHogwartsUser = hogwartsUserService.updateById(userId, hogwartsUserUpdate);
        HogwartsUserDto hogwartsUserDto = hogwartsUserToHogwartsUserDtoConverter.convert(updatedHogwartsUser);

        return new ApiResponse(
                true,
                StatusCode.SUCCESS,
                "Update Success",
                hogwartsUserDto
        );
    }

    @DeleteMapping("/{userId}")
    public ApiResponse deleteUserById(@PathVariable Integer userId){
        return null;
    }


}
