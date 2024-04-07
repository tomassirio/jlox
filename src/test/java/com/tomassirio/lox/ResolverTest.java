package com.tomassirio.lox;

import static org.junit.Assert.*;

import com.tomassirio.lox.scanner.token.Token;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;

public class ResolverTest {

    private Resolver resolver;
    private Interpreter mockInterpreter;

    @Before
    public void setup() {
        mockInterpreter = Mockito.mock(Interpreter.class);
        resolver = new Resolver(mockInterpreter);
    }

    @Test
    public void testResolveHandlesIfStmt() {
        Expr mockExpr = Mockito.mock(Expr.class);
        Stmt mockStmt = Mockito.mock(Stmt.class);
        Stmt.If mockIfStmt = new Stmt.If(mockExpr, mockStmt, mockStmt);

        resolver.resolve(Collections.singletonList(mockIfStmt));

        Mockito.verify(mockExpr).accept(resolver);
        Mockito.verify(mockStmt, Mockito.times(2)).accept(resolver);
    }

    // Similar tests for remaining Stmt processing methods...

    @Test
    public void testResolveHandlesBinaryExpr() {
        Expr mockLeft = Mockito.mock(Expr.class);
        Expr mockRight = Mockito.mock(Expr.class);
        Expr.Binary mockBinary = new Expr.Binary(mockLeft, Mockito.mock(Token.class), mockRight);

        mockBinary.accept(resolver);

        Mockito.verify(mockRight).accept(resolver);
        Mockito.verify(mockLeft).accept(resolver);
    }

    @Test
    public void testResolveHandlesUnaryExpr() {
        Expr mockExpr = Mockito.mock(Expr.class);
        Expr.Unary mockUnary = new Expr.Unary(Mockito.mock(Token.class), mockExpr);

        mockUnary.accept(resolver);

        Mockito.verify(mockExpr).accept(resolver);
    }

    // Similar tests for remaining Expr processing methods...
}