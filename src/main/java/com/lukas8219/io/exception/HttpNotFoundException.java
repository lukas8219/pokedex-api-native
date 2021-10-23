package com.lukas8219.io.exception;

import java.nio.charset.StandardCharsets;

public class HttpNotFoundException  extends HttpException{

    public HttpNotFoundException() {
        super(404);
    }

    @Override
    public byte[] getBody() {
        return "".getBytes(StandardCharsets.UTF_8);
    }

}
