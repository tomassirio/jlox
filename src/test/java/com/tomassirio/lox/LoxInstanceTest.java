package com.tomassirio.lox;

import com.tomassirio.lox.error.RuntimeError;
import com.tomassirio.lox.scanner.token.Token;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;

public class LoxInstanceTest {

    private LoxInstance instance;
    private LoxClass mockLoxClass;
    private LoxFunction mockLoxFunction;
    private Token mockToken;

    @Before
    public void setup() {
        mockLoxClass = Mockito.mock(LoxClass.class);
        mockLoxFunction = Mockito.mock(LoxFunction.class);
        mockToken = Mockito.mock(Token.class);
        instance = new LoxInstance(mockLoxClass);
        Mockito.when(mockToken.getLexeme()).thenReturn("testLexeme");
    }

    @Test
    public void testMethodReturnsFromFieldsIfPresent() {
        instance.set(mockToken, "testValue"); //Assuming you have a setter to add values to fields.
        Object value = instance.get(mockToken);
        assertEquals("testValue", value);
    }

    @Test
    public void testMethodReturnsFromMethodsIfPresent() {
        // Mocking the bound method.
        LoxFunction boundFunction = Mockito.mock(LoxFunction.class);

        // 'thenAnswer' helps handle the issue with functions that return 'this'.
        Mockito.when(mockLoxFunction.bind(any())).thenAnswer(invocation -> boundFunction);

        Mockito.when(mockLoxClass.findMethod("testLexeme")).thenReturn(mockLoxFunction);
        Mockito.when(mockToken.getLexeme()).thenReturn("testLexeme");

        Object value = instance.get(mockToken);

        assertEquals(boundFunction, value);
    }

    @Test(expected = RuntimeError.class)
    public void testMethodThrowsRuntimeErrorIfNothingFound() {
        instance.get(mockToken);
    }
}