package com.tomassirio.lox;

import com.tomassirio.lox.scanner.token.Token;
import com.tomassirio.lox.scanner.token.TokenType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExprTest {
    @Test
    void testExprCreation() {
        // create tokens
        Token token1 = new Token(TokenType.NUMBER, "1", 1, 1);
        Token token2 = new Token(TokenType.NUMBER, "2", 2, 1);
        Token plusToken = new Token(TokenType.PLUS, "+", null, 1);

        // create expressions
        Expr.Literal literal1 = new Expr.Literal(1);
        Expr.Literal literal2 = new Expr.Literal(2);
        Expr.Binary binary = new Expr.Binary(literal1, plusToken, literal2);

        // check structure
        assertEquals(1, ((Expr.Literal) binary.left).value);
        assertEquals(TokenType.PLUS, binary.operator.getType());
        assertEquals(2, ((Expr.Literal) binary.right).value);
    }
}