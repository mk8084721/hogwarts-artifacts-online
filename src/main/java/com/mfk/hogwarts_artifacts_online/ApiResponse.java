package com.mfk.hogwarts_artifacts_online;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ApiResponse {
    private boolean flag;
    private Integer code;
    private String message;
    private Object data;
    public ApiResponse(boolean flag, Integer code, String message) {
        this.flag = flag;
        this.code = code;
        this.message = message;
    }
}
