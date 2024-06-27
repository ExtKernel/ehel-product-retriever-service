package org.tes.productretrieverservice.exception;

public class WritingAuthCodeRequestBodyToJsonStringException extends RuntimeException {
    public WritingAuthCodeRequestBodyToJsonStringException(String message) {
        super(message);
    }

    public WritingAuthCodeRequestBodyToJsonStringException(String message, Throwable cause) {
        super(message, cause);
    }

    public WritingAuthCodeRequestBodyToJsonStringException(Throwable cause) {
        super(cause);
    }
}
