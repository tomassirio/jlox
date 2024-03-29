package com.tomassirio.lox;

import com.tomassirio.lox.parser.Expr;
import com.tomassirio.lox.parser.Interpreter;
import com.tomassirio.lox.parser.Parser;
import com.tomassirio.lox.parser.Stmt;
import com.tomassirio.lox.parser.error.RuntimeError;
import com.tomassirio.lox.scanner.Scanner;
import com.tomassirio.lox.scanner.token.Token;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static com.tomassirio.lox.scanner.token.TokenType.EOF;

public class Lox {

    private static final Interpreter interpreter = new Interpreter();
    static boolean hadError = false;
    static boolean hadRuntimeError = false;

    public static void main(String[] args) throws IOException {
        if(args.length > 1) {
            System.out.println("Usage: jlox [script]");
            System.exit(64);
        } else if(args.length == 1) {
            runFile(args[0]);
        } else {
            runPrompt();
        }
        System.out.println("Hello world!");
    }

    private static void runFile(String path) throws IOException {
        System.out.println("Running file: " + path);
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        run(new String(bytes, Charset.defaultCharset()));

        if(hadError) System.exit(65);
        if(hadRuntimeError) System.exit(70);
    }

    private static void runPrompt() throws IOException {
        System.out.println("Running prompt");
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input);

        for (;;) {
            hadError = false;

            System.out.print("> ");
            Scanner scanner = new Scanner(reader.readLine());
            List<Token> tokens = scanner.scanTokens();

            Parser parser = new Parser(tokens);
            Object syntax = parser.parseRepl();

            // Ignore it if there was a syntax error.
            if (hadError) continue;

            if (syntax instanceof List) {
                interpreter.interpret((List<Stmt>)syntax);
            } else if (syntax instanceof Expr) {
                String result = interpreter.interpret((Expr)syntax);
                if (result != null) {
                    System.out.println("= " + result);
                }
            }
        }
    }

    private static void run(String source) {
        Scanner scanner = new Scanner(source);
        List<Token> tokens = scanner.scanTokens();
        Parser parser = new Parser(tokens);
        List<Stmt> statements = parser.parse();

        // Stop if there was a syntax error.
        if (hadError) return;

        interpreter.interpret(statements);

        for(Token token : tokens) {
            System.out.println(token);
        }
    }

    public static void error(int line, String message) {
        report(line, "", message);
    }

    public static void runtimeError(RuntimeError error) {
        System.err.println(error.getMessage() + "\n[line " + error.getToken().getLine() + "]");
        hadRuntimeError = true;
    }
    private static void report(int line, String where, String message) {
        System.err.println("[line " + line + "] Error" + where + ": " + message);
        hadError = true;
    }

    public static void error(Token token, String message) {
        if (token.getType() == EOF) {
            report(token.getLine(), " at end", message);
        } else {
            report(token.getLine(), " at '" + token.getLexeme() + "'", message);
        }
    }
}