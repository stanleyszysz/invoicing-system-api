package pl.fc.invoicing.exceptions.handlers;

public class IdNotFoundException extends RuntimeException {

    public IdNotFoundException(String msg) {
        super(msg);
    }
}
