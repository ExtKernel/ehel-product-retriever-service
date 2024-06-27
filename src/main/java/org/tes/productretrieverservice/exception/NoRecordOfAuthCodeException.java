package org.tes.productretrieverservice.exception;

public class NoRecordOfAuthCodeException extends RuntimeException {
    public NoRecordOfAuthCodeException(String message) {
        super(message);
    }

    public NoRecordOfAuthCodeException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoRecordOfAuthCodeException(Throwable cause) {
        super(cause);
    }
}
