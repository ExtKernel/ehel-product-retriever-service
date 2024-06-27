package org.tes.productretrieverservice.exception;

public class WritingRefreshTokenRequestBodyToJsonStringException extends RuntimeException {
    public WritingRefreshTokenRequestBodyToJsonStringException(String message) {
        super(message);
    }

    public WritingRefreshTokenRequestBodyToJsonStringException(String message, Throwable cause) {
        super(message, cause);
    }

    public WritingRefreshTokenRequestBodyToJsonStringException(Throwable cause) {
        super(cause);
    }
}
