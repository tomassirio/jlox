package com.tomassirio.tool;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

public class GenerateAst {
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.err.println("Usage: generate_ast <output directory>");
            System.exit(64);
        }
        String outputDir = args[0];

        defineAst(outputDir, "Expr", Arrays.asList(
                "Binary   : Expr left, Token operator, Expr right",
                "Unary    : Token operator, Expr right",
                "Literal  : Object value",
                "Grouping : Expr expression",
                "Ternary  : Expr condition, Expr thenBranch, Expr elseBranch"
        ));
    }

    private static void defineAst(String outputDir, String baseName, List<String> types) throws IOException {
        String path = outputDir + "/" + baseName + ".java";
        PrintWriter writer = new PrintWriter(path, "UTF-8");

        writer.println("package com.tomassirio.lox.parser;");
        writer.println();
        writer.println("import com.tomassirio.lox.scanner.token.Token;");
        writer.println();
        writer.println("public abstract class " + baseName + " {");

        // The AST classes.
        for (String type : types) {
            String className = type.split(":")[0].trim(); // Binary
            String fields = type.split(":")[1].trim(); // Expr left, Token operator, Expr right
            defineType(writer, baseName, className, fields);
        }

        writer.println("}");
        writer.close();
    }

    private static void defineType(PrintWriter writer, String baseName, String className, String fieldList) {
        writer.println("    static class " + className + " extends " + baseName + " {");

        // Constructor.
        writer.println("        " + className + "(" + fieldList + ") {");

        // Store parameters in fields.
        String[] fields = fieldList.split(", ");
        for (String field : fields) {
            String name = field.split(" ")[1]; // left
            writer.println("            this." + name + " = " + name + ";");
        }

        writer.println("        }");
        writer.println();

        // Fields.
        for (String field : fields) {
            writer.println("        final " + field + ";");
        }

        writer.println("    }");
    }
}