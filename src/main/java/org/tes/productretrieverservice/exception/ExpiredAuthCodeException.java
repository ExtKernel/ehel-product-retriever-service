package org.tes.productretrieverservice.exception;

public class ExpiredAuthCodeException extends RuntimeException {
    public ExpiredAuthCodeException(String message) {
        super(message);
    }

    public ExpiredAuthCodeException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExpiredAuthCodeException(Throwable cause) {
        super(cause);
    }
}
