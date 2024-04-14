package com.tomassirio.lox;

import com.tomassirio.lox.scanner.token.Token;
import com.tomassirio.lox.scanner.token.TokenType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class InterpreterTest {
    private Interpreter interpreter;

    @BeforeEach
    public void setup() {
        interpreter = new Interpreter();
    }

    @Test
    public void testInterpret() {
        Stmt.Expression statement = Mockito.mock(Stmt.Expression.class);
        List<Stmt> statements = new ArrayList<>();
        statements.add(statement);

        // Assuming no exceptions are thrown
        assertDoesNotThrow(() -> interpreter.interpret(statements));

        // Assuming that a RuntimeError is thrown
        // Mockito.doThrow(new RuntimeError(new Token(TokenType.AND, "and", null, 1), "Test error."))
        // .when(statement).accept(any(Expr.Visitor.class));
        // assertThrows(RuntimeError.class, () -> interpreter.interpret(statements));
    }

    @Test
    public void testVisitClassStmt() {
        Token mockToken = Mockito.mock(Token.class);
        List<Stmt.Function> mockMethods = new ArrayList<>();
        Expr.Variable superClass = Mockito.mock(Expr.Variable.class);
        Stmt.Class mockClassStmt = new Stmt.Class(mockToken, superClass, mockMethods);

        assertDoesNotThrow(() -> interpreter.visitClassStmt(mockClassStmt));
    }

    @Test
    public void testVisitFunctionStmt() {
        Token mockToken = Mockito.mock(Token.class);
        List<Token> mockParams = new ArrayList<>();
        List<Stmt> mockBody = new ArrayList<>();
        Stmt.Function functionStmt = new Stmt.Function(mockToken, mockParams, mockBody);

        assertDoesNotThrow(() -> interpreter.visitFunctionStmt(functionStmt));

        // If defined function must be LoxFunction
        // Object definedFunction = interpreter.globals.get(functionStmt.name.getLexeme());
        // assertTrue(definedFunction instanceof LoxFunction);
    }

    @Test
    public void testVisitReturnStmt() {
        Expr.Literal literalExpr = Mockito.mock(Expr.Literal.class);
        Stmt.Return returnStmt = new Stmt.Return(new Token(TokenType.RETURN, "return", null, 0), literalExpr);

        // Returns can only be inside functions
        // assertThrows(RuntimeError.class, () -> interpreter.visitReturnStmt(returnStmt));
    }


    @Test
    public void testLookUpVariable() {
        String varName = "test";
        Token varToken = new Token(TokenType.IDENTIFIER, varName, null, 0);
        Expr mockExpr = Mockito.mock(Expr.class);

        // Assuming variable is not defined in globals or Local scope
        // assertThrows(RuntimeError.class, () -> interpreter.lookUpVariable(varToken, mockExpr));
    }
}