package com.tomassirio.lox.environment;

import com.tomassirio.lox.error.RuntimeError;
import com.tomassirio.lox.scanner.token.Token;

import java.util.HashMap;
import java.util.Map;

public class Environment {
    private Environment enclosing;
    private final Map<String, Object> values = new HashMap<>();

    public Environment() {
        enclosing = null;
    }

    public Environment(Environment enclosing) {
        this.enclosing = enclosing;
    }

    public void define(String name, Object value) {
        values.put(name, value);
    }

    public Object getAt(int distance, String name) {
        return ancestor(distance).values.get(name);
    }

    public void assignAt(int distance, Token name, Object value) {
        ancestor(distance).values.put(name.getLexeme(), value);
    }

    public Object get(Token name) {
        if (values.containsKey(name.getLexeme())) return values.get(name.getLexeme());
        if (enclosing != null) return enclosing.get(name);
        throw new RuntimeError(name, "Undefined variable '" + name.getLexeme() + "'.");
    }

    public void assign(Token name, Object value) {
        if (values.containsKey(name.getLexeme())) {
            values.put(name.getLexeme(), value);
            return;
        }
        if (enclosing != null) {
            enclosing.assign(name, value);
            return;
        }
        throw new RuntimeError(name, "Undefined variable '" + name.getLexeme() + "'.");
    }

    private Environment ancestor(int distance) {
        Environment environment = this;
        for (int i = 0; i < distance; i++) {
            environment = environment.enclosing;
        }
        return environment;
    }
}
