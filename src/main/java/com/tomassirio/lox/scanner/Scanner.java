package com.tomassirio.lox.scanner;

import com.tomassirio.lox.Lox;
import com.tomassirio.lox.scanner.reserved.ReservedKeyWords;
import com.tomassirio.lox.scanner.token.Token;
import com.tomassirio.lox.scanner.token.TokenType;

import java.util.ArrayList;
import java.util.List;

import static com.tomassirio.lox.scanner.token.TokenType.BANG;
import static com.tomassirio.lox.scanner.token.TokenType.BANG_EQUAL;
import static com.tomassirio.lox.scanner.token.TokenType.COMMA;
import static com.tomassirio.lox.scanner.token.TokenType.DOT;
import static com.tomassirio.lox.scanner.token.TokenType.EOF;
import static com.tomassirio.lox.scanner.token.TokenType.EQUAL;
import static com.tomassirio.lox.scanner.token.TokenType.EQUAL_EQUAL;
import static com.tomassirio.lox.scanner.token.TokenType.GREATER;
import static com.tomassirio.lox.scanner.token.TokenType.GREATER_EQUAL;
import static com.tomassirio.lox.scanner.token.TokenType.IDENTIFIER;
import static com.tomassirio.lox.scanner.token.TokenType.LEFT_BRACE;
import static com.tomassirio.lox.scanner.token.TokenType.LEFT_PAREN;
import static com.tomassirio.lox.scanner.token.TokenType.LESS;
import static com.tomassirio.lox.scanner.token.TokenType.LESS_EQUAL;
import static com.tomassirio.lox.scanner.token.TokenType.MINUS;
import static com.tomassirio.lox.scanner.token.TokenType.NUMBER;
import static com.tomassirio.lox.scanner.token.TokenType.PLUS;
import static com.tomassirio.lox.scanner.token.TokenType.RIGHT_BRACE;
import static com.tomassirio.lox.scanner.token.TokenType.RIGHT_PAREN;
import static com.tomassirio.lox.scanner.token.TokenType.SEMICOLON;
import static com.tomassirio.lox.scanner.token.TokenType.SLASH;
import static com.tomassirio.lox.scanner.token.TokenType.STAR;
import static com.tomassirio.lox.scanner.token.TokenType.STRING;

public class Scanner {

    private final String source;
    private final List<Token> tokens = new ArrayList<>();
    private int start = 0;
    private int current = 0;
    private int line = 1;

    public Scanner(String source) {
        this.source = source;
    }

    public List<Token> scanTokens() {
        while (!isAtEnd()) {
            start = current;
            scanToken();
        }

        tokens.add(new Token(EOF, "", null, line));
        return tokens;
    }

    private boolean isAtEnd() {
        return current >= source.length();
    }

    private void scanToken() {
        char c = advance();
        switch (c) {
            case '(':
                addToken(LEFT_PAREN);
                break;
            case ')':
                addToken(RIGHT_PAREN);
                break;
            case '{':
                addToken(LEFT_BRACE);
                break;
            case '}':
                addToken(RIGHT_BRACE);
                break;
            case ',':
                addToken(COMMA);
                break;
            case '.':
                addToken(DOT);
                break;
            case '-':
                addToken(MINUS);
                break;
            case '+':
                addToken(PLUS);
                break;
            case ';':
                addToken(SEMICOLON);
                break;
            case '*':
                addToken(STAR);
                break;
            case '!':
                addToken(match('=') ? BANG_EQUAL : BANG);
                break;
            case '=':
                addToken(match('=') ? EQUAL_EQUAL : EQUAL);
                break;
            case '<':
                addToken(match('=') ? LESS_EQUAL : LESS);
                break;
            case '>':
                addToken(match('=') ? GREATER_EQUAL : GREATER);
                break;
            case '/':
                if (match('/')) {
                    // A comment goes until the end of the line.
                    while (peek() != '\n' && !isAtEnd()) advance();
                } else if (match('*')) {
                    while (peek() != '*' && peekNext() != '/' && !isAtEnd()) {
                        if (peek() == '\n') line++;
                        advance();
                    }
                    if (isAtEnd()) {
                        Lox.error(line, "Unterminated comment.");
                        return;
                    }
                    advance();
                    advance();
                } else {
                    addToken(SLASH);
                }
                break;
            case ' ':
            case '\r':
            case '\t':
                // Ignore whitespace.
                break;
            case '\n':
                line++;
                break;
            case '"':
                string();
                break;

            default:
                if (isDigit(c)) {
                    number();
                } else if (isAlpha(c)) {
                    identifier();
                } else {
                    Lox.error(line, "Unexpected character: " + c);
                }
                break;
        }
    }

    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    private boolean isAlpha(char c) {
        return (c >= 'a' && c <= 'z') ||
                (c >= 'A' && c <= 'Z') ||
                c == '_';
    }

    private boolean isAlphaNumeric(char c) {
        return isAlpha(c) || isDigit(c);
    }


    private void addToken(TokenType type) {
        addToken(type, null);
    }

    private void addToken(TokenType type, Object literal) {
        String text = source.substring(start, current);
        tokens.add(new Token(type, text, literal, line));
    }


    private void string() {
        while (peek() != '"' && !isAtEnd()) {
            if (peek() == '\n') line++;
            advance();
        }

        if (isAtEnd()) {
            Lox.error(line, "Unterminated string.");
            return;
        }

        // The closing ".
        advance();

        // Trim the surrounding quotes.
        String value = source.substring(start + 1, current - 1);
        addToken(STRING, value);
    }

    private void number() {
        while (isDigit(peek())) advance();

        // Look for a fractional part.
        if (peek() == '.' && isDigit(peekNext())) {
            // Consume the "."
            advance();

            while (isDigit(peek())) advance();
        }

        addToken(NUMBER, Double.parseDouble(source.substring(start, current)));
    }

    private void identifier() {
        while (isAlphaNumeric(peek())) advance();

        String text = source.substring(start, current);
        TokenType type = ReservedKeyWords.get(text);
        if (type == null) type = IDENTIFIER;
        addToken(type);
    }

    private char peek() {
        if (isAtEnd()) return '\0';
        return source.charAt(current);
    }

    private char peekNext() {
        if (current + 1 >= source.length()) return '\0';
        return source.charAt(current + 1);
    }

    private char advance() {
        return source.charAt(current++);
    }

    private boolean match(char expected) {
        if (isAtEnd()) return false;
        if (source.charAt(current) != expected) return false;

        current++;
        return true;
    }
}
