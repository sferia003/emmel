package com.sferia.emmel;

public class Error {

  static void errToken(Token token, String message) {
    if (token.type == TokenType.EOF) {
      errLine(token.line, message + " at end");
    } else {
      errLine(token.line, message + " at '" + token.lexeme + "'");
    }
  }

  public static void errLine(int line, String message) {
    err(String.format("Line %d: %s", line, message));
  }

  public static void err(String message) {
    Emmel.errored = true;
    System.err.println(String.format("ERROR: %s", message));
  }

  static void runtimeError(RuntimeError error) {
    System.err.println("Runtime Error: " + error.getMessage() + "\n[line " + error.token.line + "]");
    Emmel.hadRuntimeError = true;
  }

}
