package org.tes.productretrieverservice.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@Slf4j
@RestControllerAdvice
public class GlobalRestExceptionHandler {

    private ErrorResponse handleException(
            Exception exception,
            WebRequest request,
            HttpStatus status,
            String message
    ) {
        log.error(message, exception);
        return new ErrorResponse(
                status.value(),
                new Date(),
                exception.getMessage(),
                request.getDescription(false)
        );
    }

    // Handle token JSON reading exceptions
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({
            AccessTokenJsonReadingException.class,
            RefreshTokenJsonReadingException.class,
            WritingAuthCodeRequestBodyToJsonStringException.class,
            WritingRefreshTokenRequestBodyToJsonStringException.class,
            ItemJsonReadingException.class,
            ModelIsNullException.class
    })
    public ErrorResponse handleTokenJsonReadingExceptions(
            Exception exception,
            WebRequest request
    ) {
        return handleException(
                exception,
                request,
                HttpStatus.INTERNAL_SERVER_ERROR,
                "An exception occurred while reading or writing token JSON: "
                        + exception.getClass().getSimpleName()
        );
    }

    // Handle expired auth code exceptions
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ExpiredAuthCodeException.class)
    public ErrorResponse handleExpiredAuthCodeException(
            ExpiredAuthCodeException exception,
            WebRequest request
    ) {
        return handleException(
                exception,
                request,
                HttpStatus.INTERNAL_SERVER_ERROR,
                "The eBay user's latest auth code has expired. Generate a new one via eBay."
        );
    }

    // Handle no record of auth model exceptions
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({
            NoRecordOfAuthCodeException.class,
            NoRecordOfAccessTokenException.class,
            NoRecordOfRefreshTokenException.class
    })
    public ErrorResponse handleNoRecordOfAuthCodeExceptions(
            Exception exception,
            WebRequest request
    ) {
        return handleException(
                exception,
                request,
                HttpStatus.NOT_FOUND,
                "No record of "
                        + exception.getClass().getSimpleName()
                        + " in the database."
        );
    }

    // Handle model not found exceptions
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ModelNotFoundException.class)
    public ErrorResponse handleModelNotFoundException(
            ModelNotFoundException exception,
            WebRequest request
    ) {
        return handleException(
                exception,
                request,
                HttpStatus.NOT_FOUND,
                "Requested model object not found in the database."
        );
    }
}
