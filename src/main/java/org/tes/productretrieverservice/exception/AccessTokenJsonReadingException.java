package org.tes.productretrieverservice.exception;

public class AccessTokenJsonReadingException extends RuntimeException {
    public AccessTokenJsonReadingException(String message) {
        super(message);
    }

    public AccessTokenJsonReadingException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccessTokenJsonReadingException(Throwable cause) {
        super(cause);
    }
}
