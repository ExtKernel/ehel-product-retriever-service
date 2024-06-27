package org.tes.productretrieverservice.exception;

public class ExpiredRefreshTokenException extends Exception {
    public ExpiredRefreshTokenException(String message) {
        super(message);
    }

    public ExpiredRefreshTokenException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExpiredRefreshTokenException(Throwable cause) {
        super(cause);
    }
}
