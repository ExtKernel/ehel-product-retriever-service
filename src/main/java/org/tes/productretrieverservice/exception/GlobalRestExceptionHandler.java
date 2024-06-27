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

    // handle token JSON reading exceptions
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(AccessTokenJsonReadingException.class)
    public ErrorResponse handleAccessTokenJsonReadingException(
            AccessTokenJsonReadingException exception,
            WebRequest request
    ) {
        log.error(
                "An exception occurred while reading"
                        + " an eBay access token JSON received from a request",
                exception
        );

        return new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                new Date(),
                exception.getMessage(),
                request.getDescription(false)
        );
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RefreshTokenJsonReadingException.class)
    public ErrorResponse handleRefreshTokenJsonReadingException(
            RefreshTokenJsonReadingException exception,
            WebRequest request
    ) {
        log.error(
                "An exception occurred while reading"
                        + " an eBay refresh token JSON received from a request",
                exception
        );

        return new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                new Date(),
                exception.getMessage(),
                request.getDescription(false)
        );
    }

    // handle an expired auth model exceptions
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ExpiredAuthCodeException.class)
    public ErrorResponse handleExpiredAuthCodeException(
            ExpiredAuthCodeException exception,
            WebRequest request
    ) {
        log.error(
                "The eBay user's latest auth code has expired." +
                        " No way to handle this issue,"
                        + " other than manually generating a new one"
                        + " via the link eBay should've been provided",
                exception
        );

        return new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                new Date(),
                exception.getMessage(),
                request.getDescription(false)
        );
    }

    // handle no record of an auth model exceptions
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoRecordOfAuthCodeException.class)
    public ErrorResponse handleNoRecordOfAuthCodeException(
            NoRecordOfAuthCodeException exception,
            WebRequest request
    ) {
        log.error(
                "There is no record of auth codes in the database",
                exception
        );

        return new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                new Date(),
                exception.getMessage(),
                request.getDescription(false)
        );
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoRecordOfAccessTokenException.class)
    public ErrorResponse handleNoRecordOfAccessTokenException(
            NoRecordOfAccessTokenException exception,
            WebRequest request
    ) {
        log.error(
                "There is no record of access tokens in the database",
                exception
        );

        return new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                new Date(),
                exception.getMessage(),
                request.getDescription(false)
        );
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoRecordOfRefreshTokenException.class)
    public ErrorResponse handleNoRecordOfRefreshTokenException(
            NoRecordOfRefreshTokenException exception,
            WebRequest request
    ) {
        log.error(
                "There is no record of refresh tokens in the database",
                exception
        );

        return new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                new Date(),
                exception.getMessage(),
                request.getDescription(false)
        );
    }

    // handle writing an auth model request body to a JSON string exceptions
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(WritingAuthCodeRequestBodyToJsonStringException.class)
    public ErrorResponse handleWritingAuthCodeRequestBodyToJsonStringException(
            WritingAuthCodeRequestBodyToJsonStringException exception,
            WebRequest request
    ) {
        log.error(
                "An exception occurred, while writing a request body,"
                        + " containing an auth code to a JSON string using the Jackson ObjectMapper",
                exception
        );

        return new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                new Date(),
                exception.getMessage(),
                request.getDescription(false)
        );
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(WritingRefreshTokenRequestBodyToJsonStringException.class)
    public ErrorResponse handleWritingRefreshTokenRequestBodyToJsonStringException(
            WritingRefreshTokenRequestBodyToJsonStringException exception,
            WebRequest request
    ) {
        log.error(
                "An exception occurred, while writing a request body,"
                        + " containing a refresh token to a JSON string using the Jackson ObjectMapper",
                exception
        );

        return new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                new Date(),
                exception.getMessage(),
                request.getDescription(false)
        );
    }

    // handle reading an item JSON exceptions
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ItemJsonReadingException.class)
    public ErrorResponse handleItemJsonReadingException(
            ItemJsonReadingException exception,
            WebRequest request
    ) {
        log.error(
                "An exception occurred while reading an eBay item JSON received from a request",
                exception
        );

        return new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                new Date(),
                exception.getMessage(),
                request.getDescription(false)
        );
    }

    // handle a model is null exceptions
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ModelIsNullException.class)
    public ErrorResponse handleModelIsNullException(
            ModelIsNullException exception,
            WebRequest request
    ) {
        log.error(
                "An exception occurred while performing an operation on a given business model object that is null",
                exception
        );

        return new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                new Date(),
                exception.getMessage(),
                request.getDescription(false)
        );
    }

    // handle a model is not found exceptions
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(ModelNotFoundException.class)
    public ErrorResponse handleModelNotFoundException(
            ModelNotFoundException exception,
            WebRequest request
    ) {
        log.error(
                "An exception occurred while retrieving a requested model object from the database, but it wasn't found",
                exception
        );

        return new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                new Date(),
                exception.getMessage(),
                request.getDescription(false)
        );
    }
}
