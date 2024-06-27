package org.tes.productretrieverservice.exception;

public class NoRecordOfRefreshTokenException extends RuntimeException {
    public NoRecordOfRefreshTokenException(String message) {
        super(message);
    }

    public NoRecordOfRefreshTokenException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoRecordOfRefreshTokenException(Throwable cause) {
        super(cause);
    }
}
