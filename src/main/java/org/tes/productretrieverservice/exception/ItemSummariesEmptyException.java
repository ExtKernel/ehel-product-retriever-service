package org.tes.productretrieverservice.exception;

public class ItemSummariesEmptyException extends RuntimeException {
    public ItemSummariesEmptyException(String message) {
        super(message);
    }

    public ItemSummariesEmptyException(String message, Throwable cause) {
        super(message, cause);
    }

    public ItemSummariesEmptyException(Throwable cause) {
        super(cause);
    }
}
