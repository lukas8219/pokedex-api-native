package com.lukas8219.io.exception;

public abstract class HttpException extends RuntimeException {

    private final int CODE;

    public HttpException(int code) {
        super(String.format("An error occurred with code %s", code));
        this.CODE = code;
    }

    public abstract byte[] getBody();

    public int getCODE() {
        return CODE;
    }

}
