package org.tes.productretrieverservice.exception;

public class AccessTokenIsNullException extends RuntimeException {
    public AccessTokenIsNullException(String message) {
        super(message);
    }

    public AccessTokenIsNullException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccessTokenIsNullException(Throwable cause) {
        super(cause);
    }
}
