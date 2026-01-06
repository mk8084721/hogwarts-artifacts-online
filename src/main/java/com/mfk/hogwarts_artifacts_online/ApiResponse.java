package com.mfk.hogwarts_artifacts_online;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ApiResponse {
    private final boolean flag;
    private final int code;
    private final String message;
    private final Object data;
}
