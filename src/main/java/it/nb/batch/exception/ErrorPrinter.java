package it.nb.batch.exception;

import org.springframework.http.HttpStatus;

public interface ErrorPrinter {

    HttpStatus getHttpStatus();

}
