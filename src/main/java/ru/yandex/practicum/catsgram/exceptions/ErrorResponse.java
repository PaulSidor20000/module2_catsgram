package ru.yandex.practicum.catsgram.exceptions;

public class ErrorResponse {

    public final String error;

    public ErrorResponse(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

}
