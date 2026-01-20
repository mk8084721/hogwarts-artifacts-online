package com.mfk.hogwarts_artifacts_online.hogwartsuser;

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
        HogwartsUser requestHogwartsUser = new HogwartsUser();
        requestHogwartsUser.setUsername(request.username());
        requestHogwartsUser.setPassword(request.password());
        requestHogwartsUser.setEnabled(request.enabled());
        requestHogwartsUser.setRoles(request.roles());

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
        return null;
    }

    @DeleteMapping("/{userId}")
    public ApiResponse deleteUserById(@PathVariable Integer userId){
        return null;
    }


}
