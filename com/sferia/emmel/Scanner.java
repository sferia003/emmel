package com.sferia.emmel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Scanner {
  private final String source;
  private final List<Token> tokens = new ArrayList<>();
  private static final Map<String, TokenType> keywords;
  private int start = 0;
  private int current = 0;
  private int line = 1;

  static {
    keywords = new HashMap<>();
    keywords.put("and", TokenType.AND);
    keywords.put("cls", TokenType.CLASS);
    keywords.put("else", TokenType.ELSE);
    keywords.put("F", TokenType.FALSE);
    keywords.put("for", TokenType.FOR);
    keywords.put("fn", TokenType.FUNCTION);
    keywords.put("if", TokenType.IF);
    keywords.put("nl", TokenType.NULL);
    keywords.put("or", TokenType.OR);
    keywords.put("pr", TokenType.PRINT);
    keywords.put("ret", TokenType.RETURN);
    keywords.put("sup", TokenType.SUPER);
    keywords.put("this", TokenType.THIS);
    keywords.put("T", TokenType.TRUE);
    keywords.put("v", TokenType.VAR);
    keywords.put("while", TokenType.WHILE);
  }

  Scanner(String source) {
    this.source = source;
  }

  List<Token> tokenize() {
    while (!EOF()) {
      start = current;
      scanToken();
    }

    tokens.add(new Token(TokenType.EOF, Config.EMPTY_STR, null, line));
    return tokens;
  }

  private void scanToken() {
    char c = next();

    switch (c) {
      case '(':
        addToken(TokenType.LEFT_PAREN);
        break;
      case ')':
        addToken(TokenType.RIGHT_PAREN);
        break;
      case '{':
        addToken(TokenType.LEFT_BRACE);
        break;
      case '}':
        addToken(TokenType.RIGHT_BRACE);
        break;
      case ',':
        addToken(TokenType.COMMA);
        break;
      case '.':
        addToken(TokenType.DOT);
        break;
      case '-':
        addToken(TokenType.MINUS);
        break;
      case '+':
        addToken(check('+') ? TokenType.CONCAT : TokenType.PLUS);
        break;
      case ';':
        addToken(TokenType.SEMICOLON);
        break;
      case '*':
        addToken(TokenType.STAR);
        break;
      case '/':
        addToken(TokenType.SLASH);
        break;
      case ' ':
      case '\r':
      case '\t':
        break;
      case '\n':
        line++;
        break;
      case '@':
        while (peek() != '\n' && !EOF())
          next();
        break;
      case '"':
        string();
        break;
      case '!':
        addToken(check('=') ? TokenType.BANG_EQUAL : TokenType.BANG);
        break;
      case '<':
        addToken(check('=') ? TokenType.LESS_EQUAL : TokenType.LESS);
        break;
      case '>':
        addToken(check('=') ? TokenType.GREATER_EQUAL : TokenType.GREATER);
        break;
      case '=':
        if (check('=')) {
          addToken(TokenType.EQUAL_EQUAL);
        } else {
          Error.errLine(line, Config.INVAL_CHARACTER);
        }
        break;
      case ':':
        if (check('=')) {
          addToken(TokenType.COLON_EQUAL);
        } else {
          Error.errLine(line, Config.INVAL_CHARACTER);
        }
        break;
      default:
        if (isDigit(c)) {
          number();
        } else if (isAlphaNumeric(c)) {
          identifier();
        } else {
          Error.errLine(line, Config.INVAL_CHARACTER);
        }
    }
  }

  private void string() {
    while (peek() != '"' && !EOF()) {
      if (peek() == '\n') {
        Error.errLine(line, Config.UNTERM_STR);
        return;
      }
      next();
    }

    if (EOF()) {
      Error.errLine(line, Config.UNTERM_STR);
      return;
    }

    next();
    String value = source.substring(start + 1, current - 1);
    addToken(TokenType.STRING, value);
  }

  private void number() {
    while (isDigit(peek()))
      next();

    if (peek() == '.' && isDigit(peekNext())) {
      next();

      while (isDigit(peek()))
        next();
    }

    addToken(TokenType.NUMBER, Double.parseDouble(source.substring(start, current)));
  }

  private boolean isAlphaNumeric(char c) {
    return isAlpha(c) || isDigit(c);
  }

  private void identifier() {
    while (isAlphaNumeric(peek()))
      next();

    String text = source.substring(start, current);
    TokenType type = keywords.get(text);
    if (type == null)
      type = TokenType.IDENTIFIER;
    addToken(type);
  }

  private char peekNext() {
    if (current + 1 >= source.length())
      return '\0';
    return source.charAt(current + 1);
  }

  private boolean check(char expected) {
    if (EOF())
      return false;
    if (source.charAt(current) == expected) {
      current++;
      return true;
    }

    return false;
  }

  private void addToken(TokenType type) {
    addToken(type, null);
  }

  private void addToken(TokenType type, Object literal) {
    String text = source.substring(start, current);
    tokens.add(new Token(type, text, literal, line));
  }

  private boolean isAlpha(char c) {
    return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
  }

  private boolean isDigit(char c) {
    return c >= '0' && c <= '9';
  }

  private char peek() {
    if (EOF())
      return '\0';
    return source.charAt(current);
  }

  private boolean EOF() {
    return current >= source.length();
  }

  private char next() {
    return source.charAt(current++);
  }

}
