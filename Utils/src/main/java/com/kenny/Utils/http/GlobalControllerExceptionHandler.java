package com.kenny.Utils.http;

import com.kenny.Utils.exceptions.InvalidInputException;
import com.kenny.Utils.exceptions.NotFoundException;
import com.kenny.Utils.exceptions.NotManufacturedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class GlobalControllerExceptionHandler {
    private static final Logger LOG = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);


    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public HttpErrorInfo handleNotFoundException(ServerHttpRequest request, Exception ex){
        return createHttpErrorInfo(NOT_FOUND, request, ex);
    }
    @ResponseStatus(UNPROCESSABLE_ENTITY)
    @ExceptionHandler(InvalidInputException.class)
    public HttpErrorInfo handleInvalidInputException(ServerHttpRequest request, Exception ex){
        return createHttpErrorInfo(UNPROCESSABLE_ENTITY, request, ex);
    }

    @ResponseStatus(GONE)
    @ExceptionHandler(NotManufacturedException.class)
    public HttpErrorInfo handleNotManufacturedException(ServerHttpRequest request, Exception ex){
        return createHttpErrorInfo(GONE, request, ex);
    }


    public HttpErrorInfo createHttpErrorInfo(HttpStatus httpStatus, ServerHttpRequest request, Exception ex){
        final String path = request.getPath().pathWithinApplication().value();
        final String message = ex.getMessage();
        LOG.debug("Returning HTTP status: {} for path: {}, message{}", httpStatus, path, message);
        return new HttpErrorInfo(httpStatus, path, message);
    }
}

