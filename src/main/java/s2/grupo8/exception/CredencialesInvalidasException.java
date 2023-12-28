package s2.grupo8.exception;


public class CredencialesInvalidasException extends RuntimeException {

    public CredencialesInvalidasException(String message) {
        super(message);
    }

    public CredencialesInvalidasException(String message, Throwable cause) {
        super(message, cause);
    }
}

