package pl.fc.invoicing.exceptions.handlers;

import lombok.Getter;

@Getter
public class ErrorMessage {

    private final int statusCode;
    private final String message;

    public ErrorMessage(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}
