package org.tes.productretrieverservice.exception;

public class ItemJsonReadingException extends RuntimeException {
    public ItemJsonReadingException(String message) {
        super(message);
    }

    public ItemJsonReadingException(String message, Throwable cause) {
        super(message, cause);
    }

    public ItemJsonReadingException(Throwable cause) {
        super(cause);
    }
}
