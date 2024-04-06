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
        Scanner scanner = new Scanner("( - ) * / ; // comment");
        List<Token> tokens = scanner.scanTokens();

        assertEquals(LEFT_PAREN, tokens.get(0).getType());
        assertEquals(MINUS, tokens.get(1).getType());
        assertEquals(RIGHT_PAREN, tokens.get(2).getType());
        assertEquals(STAR, tokens.get(3).getType());
        assertEquals(SLASH, tokens.get(4).getType());
        assertEquals(SEMICOLON, tokens.get(5).getType());
        assertEquals(EOF, tokens.get(6).getType());
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

    @Test
    void testScanStringToken() {
        Scanner scanner = new Scanner("\"Hello, World!\"");
        List<Token> tokens = scanner.scanTokens();

        assertEquals(STRING, tokens.get(0).getType());
        assertEquals("Hello, World!", tokens.get(0).getLiteral());
    }

    @Test
    void testScanNumberToken() {
        Scanner scanner = new Scanner("123.456");
        List<Token> tokens = scanner.scanTokens();

        assertEquals(NUMBER, tokens.get(0).getType());
        assertEquals(123.456, tokens.get(0).getLiteral());
    }

    @Test
    void testScanReservedWords() {
        final String source = "and class else false fun for if nil or print return super this true var while";
        Scanner scanner = new Scanner(source);
        List<Token> tokens = scanner.scanTokens();

        TokenType[] expectedTokenTypes = {AND, CLASS, ELSE, FALSE, FUN, FOR, IF, NIL, OR, PRINT, RETURN, SUPER, THIS, TRUE, VAR, WHILE};

        for (int i = 0; i < expectedTokenTypes.length; i++) {
            assertEquals(expectedTokenTypes[i], tokens.get(i).getType());
        }
    }

    @Test
    void testScanIdentifiers() {
        Scanner scanner = new Scanner("foo bar1 _baz");
        List<Token> tokens = scanner.scanTokens();

        for (Token token : tokens.subList(0, tokens.size() - 1)) {
            assertEquals(IDENTIFIER, token.getType());
        }
    }
}