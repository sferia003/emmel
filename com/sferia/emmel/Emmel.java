package com.sferia.emmel;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Emmel {
  private static final Interpreter interpreter = new Interpreter();
  static boolean errored = false;
  static boolean hadRuntimeError = false;

  public static void main(String[] args) throws IOException {
    if (args.length > 1 || args.length == 0) {
      System.out.println(Config.USAGE);
      System.exit(64);
    } else {
      execute(args[0]);
    }
  }

  private static void execute(String path) throws IOException {
    byte[] bytes = Files.readAllBytes(Paths.get(path));

    Scanner scanner = new Scanner(new String(bytes, Charset.defaultCharset()));
    List<Token> tokens = scanner.tokenize();

    Parser parser = new Parser(tokens);
    List<Stmt> statements = parser.parse();

    if (errored)
      System.exit(64);

    Resolver resolver = new Resolver(interpreter);
    resolver.resolve(statements);

    if (errored)
      System.exit(64);

    interpreter.interpret(statements);

    if (hadRuntimeError)
      System.exit(70);
  }
}
