package ru.yandex.practicum.catsgram.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.catsgram.exceptions.*;

@RestControllerAdvice
public class ErrorHandler {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({PostNotFoundException.class, UserNotFoundException.class})
    public ErrorResponse userAndPostNotFoundHandler(final RuntimeException e) {
        return new ErrorResponse(e.getMessage());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler({UserAlreadyExistException.class})
    public ErrorResponse userAlreadyExistHandler(final RuntimeException e) {
        return new ErrorResponse(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({InvalidEmailException.class})
    public ErrorResponse invalidEmailHandler(final RuntimeException e) {
        return new ErrorResponse(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ErrorResponse incorrectParameterHandler(final IncorrectParameterException e) {
        return new ErrorResponse(e.getMessage() + e.getParameter());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorResponse otherExceptionHandler(final Throwable e) {
        return new ErrorResponse("An unexpected error has occurred.");
    }

}