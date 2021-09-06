package com.sferia.emmel;

import java.util.List;

abstract class Stmt {
    interface Visitor<T> {
        T visitExpressionStmt(Expression stmt);

        T visitFunctionStmt(Function stmt);

        T visitPrintStmt(Print stmt);

        T visitReturnStmt(Return stmt);

        T visitVarStmt(Var stmt);

        T visitWhileStmt(While stmt);

        T visitBlockStmt(Block stmt);

        T visitClassStmt(Class stmt);

        T visitIfStmt(If stmt);
    }

    static class Expression extends Stmt {
        Expression(Expr expression) {
            this.expression = expression;
        }

        @Override
        <T> T accept(Visitor<T> visitor) {
            return visitor.visitExpressionStmt(this);
        }

        final Expr expression;
    }

    static class Function extends Stmt {
        Function(Token name, List<Token> params, List<Stmt> body) {
            this.name = name;
            this.params = params;
            this.body = body;
        }

        @Override
        <T> T accept(Visitor<T> visitor) {
            return visitor.visitFunctionStmt(this);
        }

        final Token name;
        final List<Token> params;
        final List<Stmt> body;
    }

    static class Print extends Stmt {
        Print(Expr expression) {
            this.expression = expression;
        }

        @Override
        <T> T accept(Visitor<T> visitor) {
            return visitor.visitPrintStmt(this);
        }

        final Expr expression;
    }

    static class Return extends Stmt {
        Return(Token keyword, Expr value) {
            this.keyword = keyword;
            this.value = value;
        }

        @Override
        <T> T accept(Visitor<T> visitor) {
            return visitor.visitReturnStmt(this);
        }

        final Token keyword;
        final Expr value;
    }

    static class Var extends Stmt {
        Var(Token name, Expr initializer) {
            this.name = name;
            this.initializer = initializer;
        }

        @Override
        <T> T accept(Visitor<T> visitor) {
            return visitor.visitVarStmt(this);
        }

        final Token name;
        final Expr initializer;
    }

    static class While extends Stmt {
        While(Expr condition, Stmt body) {
            this.condition = condition;
            this.body = body;
        }

        @Override
        <T> T accept(Visitor<T> visitor) {
            return visitor.visitWhileStmt(this);
        }

        final Expr condition;
        final Stmt body;
    }

    static class Block extends Stmt {
        Block(List<Stmt> statements) {
            this.statements = statements;
        }

        @Override
        <T> T accept(Visitor<T> visitor) {
            return visitor.visitBlockStmt(this);
        }

        final List<Stmt> statements;
    }

    static class Class extends Stmt {
        Class(Token name, Expr.Variable superclass, List<Stmt.Function> methods) {
            this.name = name;
            this.superclass = superclass;
            this.methods = methods;
        }

        @Override
        <T> T accept(Visitor<T> visitor) {
            return visitor.visitClassStmt(this);
        }

        final Token name;
        final Expr.Variable superclass;
        final List<Stmt.Function> methods;
    }

    static class If extends Stmt {
        If(Expr condition, Stmt thenBranch, Stmt elseBranch) {
            this.condition = condition;
            this.thenBranch = thenBranch;
            this.elseBranch = elseBranch;
        }

        @Override
        <T> T accept(Visitor<T> visitor) {
            return visitor.visitIfStmt(this);
        }

        final Expr condition;
        final Stmt thenBranch;
        final Stmt elseBranch;
    }

    abstract <T> T accept(Visitor<T> visitor);
}
