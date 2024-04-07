package com.tomassirio.lox;

import com.tomassirio.lox.error.RuntimeError;
import com.tomassirio.lox.scanner.token.Token;

import java.util.HashMap;
import java.util.Map;

public class LoxInstance {
    private final LoxClass klass;
    private final Map<String, Object> fields = new HashMap<>();

    public LoxInstance(LoxClass klass) {
        this.klass = klass;
    }

    @Override
    public String toString() {
        return klass.name + " instance";
    }

    public Object get(Token name) {
        if (fields.containsKey(name.getLexeme())) {
            return fields.get(name.getLexeme());
        }

        LoxFunction method = klass.findMethod(name.getLexeme());
        if (method != null) return method.bind(this);

        throw new RuntimeError(name, "Undefined property '"+ name.getLexeme() + "'.");
    }

    public void set(Token name, Object value) {
        fields.put(name.getLexeme(), value);
    }

    public LoxClass getKlass() {
        return klass;
    }
}
