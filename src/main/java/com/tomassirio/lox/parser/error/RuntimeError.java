package com.tomassirio.lox.parser.error;

import com.tomassirio.lox.scanner.token.Token;

public class RuntimeError extends RuntimeException{
    final Token token;

    public RuntimeError(Token token, String message) {
        super(message);
        this.token = token;
    }

    public Token getToken() {
        return token;
    }
}
