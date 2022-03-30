package pl.fc.invoicing.exceptions.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler({
        IdNotFoundException.class
    })
    @Nullable
    public final ResponseEntity<String> handleException(Exception ex) {
        if (ex instanceof IdNotFoundException) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            IdNotFoundException infe = (IdNotFoundException) ex;
            return idNotFoundException(infe, status);
        } else {
            return globalExceptionHandler(ex);
        }
    }

    public ResponseEntity<String> idNotFoundException(IdNotFoundException ex, HttpStatus status) {
        return new ResponseEntity<>(ex.getMessage(), status);
    }

    public ResponseEntity<String> globalExceptionHandler(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
