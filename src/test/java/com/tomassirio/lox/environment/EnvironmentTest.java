package com.tomassirio.lox.environment;

import com.tomassirio.lox.error.RuntimeError;
import com.tomassirio.lox.scanner.token.Token;
import com.tomassirio.lox.scanner.token.TokenType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EnvironmentTest {
    @Test
    void defineAndGetVariable() {
        Environment environment = new Environment();
        Token token = new Token(TokenType.IDENTIFIER, "x", null, 1);

        environment.define("x", 10);
        Object result = environment.get(token);

        assertEquals(result, 10);
    }

    @Test
    void getUndefinedVariable() {
        Environment environment = new Environment();
        Token token = new Token(TokenType.IDENTIFIER, "x", null, 1);

        assertThrows(RuntimeError.class, () -> environment.get(token));
    }

    @Test
    void defineAndAssignVariable() {
        Environment environment = new Environment();
        Token token = new Token(TokenType.IDENTIFIER, "x", null, 1);

        environment.define("x", 10);
        environment.assign(token, 20);
        Object result = environment.get(token);

        assertEquals(result, 20);
    }

    @Test
    void assignUndefinedVariable() {
        Environment environment = new Environment();
        Token token = new Token(TokenType.IDENTIFIER, "x", null, 1);

        assertThrows(RuntimeError.class, () -> environment.assign(token, 20));
    }

    @Test
    void getFromEnclosingEnvironment() {
        Environment global = new Environment();
        Environment local = new Environment(global);
        Token token = new Token(TokenType.IDENTIFIER, "x", null, 1);

        global.define("x", 10);
        Object result = local.get(token);

        assertEquals(result, 10);
    }

    @Test
    void assignInEnclosingEnvironment() {
        Environment global = new Environment();
        Environment local = new Environment(global);
        Token token = new Token(TokenType.IDENTIFIER, "x", null, 1);

        global.define("x", 10);
        local.assign(token, 20);
        Object result = global.get(token);

        assertEquals(result, 20);
    }

    @Test
    void assignAndGetAt() {
        Environment global = new Environment();
        Environment firstLocal = new Environment(global);
        Environment secondLocal = new Environment(firstLocal);
        Token token = new Token(TokenType.IDENTIFIER, "x", null, 1);

        secondLocal.define("x", 10);
        secondLocal.assignAt(2, token, 20);
        Object result = secondLocal.getAt(2, "x");

        assertEquals(result, 20);
    }

    @Test
    void assignNullValue() {
        Environment environment = new Environment();
        Token token = new Token(TokenType.IDENTIFIER, "x", null, 1);
        environment.define("x", 10);
        environment.assign(token, null);

        Object result = environment.get(token);

        assertNull(result);
    }

    @Test
    void getNonExistentVariable() {
        Environment environment = new Environment();
        Token token = new Token(TokenType.IDENTIFIER, "y", null, 1);

        assertThrows(RuntimeError.class, () -> environment.get(token));
    }
}