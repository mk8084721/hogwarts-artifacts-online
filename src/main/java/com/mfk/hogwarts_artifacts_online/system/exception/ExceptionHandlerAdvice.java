package com.mfk.hogwarts_artifacts_online.system.exception;

import com.mfk.hogwarts_artifacts_online.system.ApiResponse;
import com.mfk.hogwarts_artifacts_online.system.StatusCode;
import com.mfk.hogwarts_artifacts_online.artifact.ArtifactNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerAdvice {
    @ExceptionHandler(ArtifactNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ApiResponse handleArtifactNotFoundException(ArtifactNotFoundException ex){
        return new ApiResponse(
                false,
                StatusCode.NOT_FOUND,
                ex.getMessage()
        );
    }
}
