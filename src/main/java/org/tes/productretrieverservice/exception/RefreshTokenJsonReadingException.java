package org.tes.productretrieverservice.exception;

public class RefreshTokenJsonReadingException extends RuntimeException {
    public RefreshTokenJsonReadingException(String message) {
        super(message);
    }

    public RefreshTokenJsonReadingException(String message, Throwable cause) {
        super(message, cause);
    }

    public RefreshTokenJsonReadingException(Throwable cause) {
        super(cause);
    }
}
