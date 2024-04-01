package com.tomassirio.lox.parser;

import com.tomassirio.lox.Parser;
import com.tomassirio.lox.Stmt;
import com.tomassirio.lox.error.ParseError;
import com.tomassirio.lox.scanner.token.Token;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static com.tomassirio.lox.scanner.token.TokenType.EOF;
import static com.tomassirio.lox.scanner.token.TokenType.NUMBER;
import static com.tomassirio.lox.scanner.token.TokenType.PLUS;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

class ParserTest {

    private Parser parser;

    @BeforeEach
    void setUp() {
        List<Token> tokens = Arrays.asList(
                new Token(NUMBER, "1", 1, 1),
                new Token(PLUS, "+", null, 1),
                new Token(NUMBER, "2", 2, 1),
                new Token(EOF, "", null, 2)
        );

        this.parser = new Parser(tokens);
    }

    @Test
    void testParse() {
        try {
            List<Stmt> statements = parser.parse();
            assertNotNull(statements);
        } catch (ParseError e) {
            fail("Parsing threw an unexpected exception");
        }
    }

    @Test
    void testEmptyTokenList() {
        List<Token> tokens = List.of(new Token(EOF, "", null, 0));
        Parser parser = new Parser(tokens);
        List<Stmt> statements = parser.parse();
        assertNull(statements);
    }
}