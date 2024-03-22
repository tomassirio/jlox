package com.tomassirio.lox.scanner;

import com.tomassirio.lox.scanner.token.Token;
import com.tomassirio.lox.scanner.token.TokenType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.tomassirio.lox.scanner.token.TokenType.*;
import static org.junit.jupiter.api.Assertions.*;

class ScannerTest {

    private Scanner scanner;

    @BeforeEach
    void setUp() {
        String source = "(1 + 2) * 3";
        this.scanner = new Scanner(source);
    }

    @Test
    void testScanTokens() {
        List<Token> tokens = scanner.scanTokens();

        // Assert that the correct number of tokens were scanned.
        assertEquals(8, tokens.size());

        // Assert that the first token is a left parenthesis.
        assertEquals(LEFT_PAREN, tokens.get(0).getType());

        // Assert that the last token is EOF.
        assertEquals(EOF, tokens.get(tokens.size() - 1).getType());
    }

    @Test
    void testEmptySource() {
        String source = "";
        Scanner scanner = new Scanner(source);
        List<Token> tokens = scanner.scanTokens();
        assertEquals(1, tokens.size());
        assertEquals(EOF, tokens.get(0).getType());
    }

    @Test
    void testWhitespaces() {
        String source = "    \n    \t    ";
        Scanner scanner = new Scanner(source);
        List<Token> tokens = scanner.scanTokens();
        assertEquals(1, tokens.size());
        assertEquals(EOF, tokens.get(0).getType());
    }

    @Test
    void testComment() {
        String source = "// this is a comment";
        Scanner scanner = new Scanner(source);
        List<Token> tokens = scanner.scanTokens();
        assertEquals(1, tokens.size());
        assertEquals(EOF, tokens.get(0).getType());
    }

    @Test
    void testNumberWithFractionalComponent() {
        String source = "3.14159";
        Scanner scanner = new Scanner(source);
        List<Token> tokens = scanner.scanTokens();
        assertEquals(2, tokens.size());  // number and EOF
        assertEquals(NUMBER, tokens.get(0).getType());
        assertEquals(3.14159, tokens.get(0).getLiteral());
    }
}