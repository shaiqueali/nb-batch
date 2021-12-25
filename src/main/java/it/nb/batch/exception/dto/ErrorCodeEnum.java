package it.nb.batch.exception.dto;

import it.nb.batch.exception.ErrorPrinter;
import org.springframework.http.HttpStatus;

public enum ErrorCodeEnum implements ErrorPrinter {

    UNAUTHORIZED(HttpStatus.UNAUTHORIZED),
    INVALID_PARAM(HttpStatus.BAD_REQUEST),
    ENTITY_NOT_FOUND(HttpStatus.BAD_REQUEST);

    ErrorCodeEnum(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    private final HttpStatus httpStatus;

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
