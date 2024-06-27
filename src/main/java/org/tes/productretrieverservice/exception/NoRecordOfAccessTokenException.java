package org.tes.productretrieverservice.exception;

public class NoRecordOfAccessTokenException extends RuntimeException {
    public NoRecordOfAccessTokenException(String message) {
        super(message);
    }

    public NoRecordOfAccessTokenException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoRecordOfAccessTokenException(Throwable cause) {
        super(cause);
    }
}
