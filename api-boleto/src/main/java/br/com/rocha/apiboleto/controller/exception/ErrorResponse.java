package br.com.rocha.apiboleto.controller.exception;

import lombok.*;
import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    private String error;
    private int code;
    private Date timestamp;
    private String path;
}
