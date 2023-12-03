package com.tomassirio.lox.parser;

import com.tomassirio.lox.parser.exception.ParseErrorException;
import com.tomassirio.lox.scanner.token.Token;
import com.tomassirio.lox.scanner.token.TokenType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {

    private Parser parser;

    @BeforeEach
    void setUp() {
        List<Token> tokens = Arrays.asList(
                new Token(TokenType.NUMBER, "1", 1, 1),
                new Token(TokenType.PLUS, "+", null, 1),
                new Token(TokenType.NUMBER, "2", 2, 1),
                new Token(TokenType.EOF, "", null, 2)
        );

        this.parser = new Parser(tokens);
    }

    @Test
    void testParse() {
        try {
            Expr expr = parser.parse();
            assertNotNull(expr);
        } catch (ParseErrorException e) {
            fail("Parsing threw an unexpected exception");
        }
    }

    @Test
    void testEmptyTokenList() {
        List<Token> tokens = List.of(new Token(TokenType.EOF, "", null, 0));
        Parser parser = new Parser(tokens);
        Expr result = parser.parse();
        assertNull(result);
    }
}