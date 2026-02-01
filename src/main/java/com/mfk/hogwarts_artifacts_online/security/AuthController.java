package com.mfk.hogwarts_artifacts_online.security;

import com.mfk.hogwarts_artifacts_online.system.ApiResponse;
import com.mfk.hogwarts_artifacts_online.system.StatusCode;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.endpoint.base-url}/users")
@RequiredArgsConstructor
public class AuthController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);
    private final AuthService authService;

    @PostMapping("/login")
    public ApiResponse getLoginInfo(Authentication authentication){
        LOGGER.debug("Authenticated user : '{}'", authentication.getName());
        return new ApiResponse(
                true,
                StatusCode.SUCCESS,
                "User info and JSON web Token",
                this.authService.createLoginInfo(authentication)
        );
    }
}
