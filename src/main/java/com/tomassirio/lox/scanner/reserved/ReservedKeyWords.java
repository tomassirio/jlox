package com.tomassirio.lox.scanner.reserved;

import com.tomassirio.lox.scanner.token.TokenType;

import java.util.HashMap;
import java.util.Map;

import static com.tomassirio.lox.scanner.token.TokenType.*;

public class ReservedKeyWords {

    private static final Map<String, TokenType> keywords;

    static {
        keywords = new HashMap<>();
        keywords.put("and", AND);
        keywords.put("class", CLASS);
        keywords.put("else", ELSE);
        keywords.put("false", FALSE);
        keywords.put("for", FOR);
        keywords.put("fun", FUN);
        keywords.put("if", IF);
        keywords.put("nil", NIL);
        keywords.put("or", OR);
        keywords.put("print", PRINT);
        keywords.put("return", RETURN);
        keywords.put("super", SUPER);
        keywords.put("this", THIS);
        keywords.put("true", TRUE);
        keywords.put("var", VAR);
        keywords.put("while", WHILE);
    }

    public static TokenType get(String lexeme) {
        return keywords.get(lexeme);
    }
}
