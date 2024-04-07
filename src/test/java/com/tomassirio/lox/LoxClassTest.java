package com.tomassirio.lox;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class LoxClassTest {

    private LoxClass loxClass;
    private LoxFunction mockLoxFunction;
    private String testName = "testFunction";

    @Before
    public void setup() {
        Map<String, LoxFunction> methods = new HashMap<>();
        mockLoxFunction = Mockito.mock(LoxFunction.class);
        methods.put(testName, mockLoxFunction);
        loxClass = new LoxClass("TestClass", methods);
    }

    @Test
    public void testFindMethodReturnsMethodIfPresent() {
        assertEquals(mockLoxFunction, loxClass.findMethod(testName));
    }

    @Test
    public void testFindMethodReturnsNullIfNotPresent() {
        assertNull(loxClass.findMethod("NoSuchMethod"));
    }

    @Test
    public void testArityReturnsZero() {
        assertEquals(0, loxClass.arity());
    }

    @Test
    public void testCallReturnsLoxInstance() {
        Interpreter mockInterpreter = Mockito.mock(Interpreter.class);
        List<Object> mockList = Mockito.mock(List.class);

        Object result = loxClass.call(mockInterpreter, mockList);

        assertTrue(result instanceof LoxInstance);
        assertEquals(loxClass, ((LoxInstance)result).getKlass());
    }
}