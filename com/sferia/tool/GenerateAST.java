package com.sferia.tool;

import com.sferia.emmel.Config;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

public class GenerateAST {
  public static void main(String[] args) throws IOException {
    if (args.length != 1) {
      System.err.println(Config.USAGE);
      System.exit(64);
    }
    String outputDir = args[0];
    defineAst(outputDir, "Expr",
        Arrays.asList("Binary: Expr left, Token operator, Expr right",
            "Call: Expr callee, Token paren, List<Expr> arguments", "Get: Expr object, Token name",
            "Set: Expr object, Token name, Expr value", "This: Token keyword", "Super: Token keyword, Token method",
            "Grouping: Expr expression", "Literal  : Object value", "Logical: Expr left, Token operator, Expr right",
            "Unary: Token operator, Expr right", "Variable : Token name", "Assign: Token name, Expr value"));
    defineAst(outputDir, "Stmt",
        Arrays.asList("Expression : Expr expression", "Function: Token name, List<Token> params," + " List<Stmt> body",
            "Print : Expr expression", "Return: Token keyword, Expr value", "Var : Token name, Expr initializer",
            "While: Expr condition, Stmt body", "Block : List<Stmt> statements",
            "Class: Token name, Expr.Variable superclass, List<Stmt.Function> methods",
            "If: Expr condition, Stmt thenBranch," + " Stmt elseBranch"));
  }

  private static void defineAst(String outputDir, String baseName, List<String> types) throws IOException {
    String path = outputDir + "/" + baseName + ".java";
    PrintWriter writer = new PrintWriter(path, "UTF-8");
    writer.println("package com.sferia.emmel;");
    writer.println();
    writer.println("import java.util.List;");
    writer.println();
    writer.println("abstract class " + baseName + " {");
    defineVisitor(writer, baseName, types);
    for (String type : types) {
      String className = type.split(":")[0].trim();
      String fields = type.split(":")[1].trim();
      defineType(writer, baseName, className, fields);
    }
    writer.println();
    writer.println("abstract <T> T accept(Visitor<T> visitor);");
    writer.println("}");
    writer.close();
  }

  private static void defineType(PrintWriter writer, String baseName, String className, String fieldList) {
    writer.println("static class " + className + " extends " + baseName + "{");
    writer.println(" " + className + "(" + fieldList + ") {");
    String[] fields = fieldList.split(", ");
    for (String field : fields) {
      String name = field.split(" ")[1];
      writer.println("this." + name + " = " + name + ";");
    }
    writer.println("}");
    writer.println();
    writer.println("@Override");
    writer.println("<T> T accept(Visitor<T> visitor) {");
    writer.println("return visitor.visit" + className + baseName + "(this);");
    writer.println("}");
    writer.println();
    for (String field : fields) {
      writer.println("final " + field + ";");
    }
    writer.println("}");
  }

  private static void defineVisitor(PrintWriter writer, String baseName, List<String> types) {
    writer.println("interface Visitor<T> {");

    for (String type : types) {
      String typeName = type.split(":")[0].trim();
      writer.println("T visit" + typeName + baseName + "(" + typeName + " " + baseName.toLowerCase() + ");");
    }

    writer.println(" }");
  }
}
