package com.tomassirio.lox;

import com.tomassirio.lox.Parser;
import com.tomassirio.lox.Stmt;
import com.tomassirio.lox.error.ParseError;
import com.tomassirio.lox.scanner.token.Token;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.tomassirio.lox.scanner.token.TokenType.*;
import static org.junit.jupiter.api.Assertions.*;

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
        List<Token> tokens = List.of(new Token(EOF, "", null, 1));
        Parser parser = new Parser(tokens);
        List<Stmt> statements = parser.parse();
        assertTrue(statements.isEmpty());
    }

    @Test
    void testVariableDecl() {
        List<Token> tokens = Arrays.asList(
                new Token(VAR, "var", null, 1),
                new Token(IDENTIFIER, "x", null, 1),
                new Token(SEMICOLON, ";", null, 1),
                new Token(EOF, "", null, 1)
        );
        Parser parser = new Parser(tokens);
        assertDoesNotThrow(() -> {
            List<Stmt> statements = parser.parse();
            assertNotNull(statements);
        });
    }

    @Test
    void testVariableDeclWithInitializer() {
        List<Token> tokens = Arrays.asList(
                new Token(VAR, "var", null, 1),
                new Token(IDENTIFIER, "x", null, 1),
                new Token(EQUAL, "=", null, 1),
                new Token(NUMBER, "1", 1, 1),
                new Token(SEMICOLON, ";", null, 1),
                new Token(EOF, "", null, 1)
        );
        Parser parser = new Parser(tokens);
        assertDoesNotThrow(() -> {
            List<Stmt> statements = parser.parse();
            assertNotNull(statements);
        });
    }

    @Test
    void testComparisonExpression() {
        List<Token> tokens = Arrays.asList(
                new Token(NUMBER, "1", 1, 1),
                new Token(LESS, "<", null, 1),
                new Token(NUMBER, "2", 2, 1),
                new Token(EOF, "", null, 2)
        );
        Parser parser = new Parser(tokens);
        assertDoesNotThrow(() -> {
            List<Stmt> statements = parser.parse();
            assertNotNull(statements);
        });
    }

    @Test
    void testFunctionDeclaration() {
        List<Token> tokens = Arrays.asList(
                new Token(FUN, "fun", null, 1),
                new Token(IDENTIFIER, "foo", null, 1),
                new Token(LEFT_PAREN, "(", null, 1),
                new Token(RIGHT_PAREN, ")", null, 1),
                new Token(LEFT_BRACE, "{", null, 1),
                new Token(RIGHT_BRACE, "}", null, 1),
                new Token(EOF, "", null, 1)
        );
        Parser parser = new Parser(tokens);
        assertDoesNotThrow(() -> {
            List<Stmt> statements = parser.parse();
            assertNotNull(statements);
        });
    }

    @Test
    void testIfStatement() {
        List<Token> tokens = Arrays.asList(
                new Token(IF, "if", null, 1),
                new Token(LEFT_PAREN, "(", null, 1),
                new Token(TRUE, "true", true, 1),
                new Token(RIGHT_PAREN, ")", null, 1),
                new Token(LEFT_BRACE, "{", null, 1),
                new Token(RIGHT_BRACE, "}", null, 1),
                new Token(EOF, "", null, 1)
        );
        Parser parser = new Parser(tokens);
        assertDoesNotThrow(() -> {
            List<Stmt> statements = parser.parse();
            assertNotNull(statements);
        });
    }

    @Test
    void testBinaryExpression() {
        List<Token> tokens = Arrays.asList(
                new Token(NUMBER, "1", 1, 1),
                new Token(PLUS, "+", null, 1),
                new Token(NUMBER, "2", 2, 1),
                new Token(STAR, "*", null, 1),
                new Token(NUMBER, "3", 3, 1),
                new Token(EOF, "", null, 1)
        );
        Parser parser = new Parser(tokens);
        assertDoesNotThrow(() -> {
            List<Stmt> statements = parser.parse();
            assertNotNull(statements);
        });
    }

    @Test
    void testLogicalExpression() {
        List<Token> tokens = Arrays.asList(
                new Token(TRUE, "true", true, 1),
                new Token(OR, "or", null, 1),
                new Token(FALSE, "false", false, 1),
                new Token(EOF, "", null, 1)
        );
        Parser parser = new Parser(tokens);
        assertDoesNotThrow(() -> {
            List<Stmt> statements = parser.parse();
            assertNotNull(statements);
        });
    }

    @Test
    void testInvalidExpression() {
        List<Token> tokens = Arrays.asList(
                new Token(NUMBER, "1", 1, 1),
                new Token(PLUS, "+", null, 1),
                new Token(PLUS, "+", null, 1), // invalid
                new Token(NUMBER, "2", 2, 1),
                new Token(EOF, "", null, 2)
        );
        Parser parser = new Parser(tokens);
        assertThrows(ParseError.class, parser::parse);
    }

    @Test
    void testUnterminatedExpression() {
        List<Token> tokens = Arrays.asList(
                new Token(VAR, "var", null, 1),
                new Token(IDENTIFIER, "x", null, 1),
                // missing SEMICOLON
                new Token(EOF, "", null, 2)
        );
        Parser parser = new Parser(tokens);
        assertThrows(ParseError.class, parser::parse);
    }

    @Test
    void testIncorrectOrderOfTokens() {
        List<Token> tokens = Arrays.asList(
                new Token(NUMBER, "2", 2, 1),
                new Token(PLUS, "+", null, 1),
                new Token(NUMBER, "1", 1, 1)
        );
        // reverse the list of tokens
        Collections.reverse(tokens);
        Parser parser = new Parser(tokens);
        assertThrows(ParseError.class, parser::parse);
    }

}