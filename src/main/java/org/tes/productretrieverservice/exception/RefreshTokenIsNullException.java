package org.tes.productretrieverservice.exception;

public class RefreshTokenIsNullException extends ModelIsNullException {
    public RefreshTokenIsNullException(String message) {
        super(message);
    }

    public RefreshTokenIsNullException(String message, Throwable cause) {
        super(message, cause);
    }

    public RefreshTokenIsNullException(Throwable cause) {
        super(cause);
    }
}
