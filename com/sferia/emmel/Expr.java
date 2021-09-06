package com.sferia.emmel;

import java.util.List;

abstract class Expr {
    interface Visitor<T> {
        T visitBinaryExpr(Binary expr);

        T visitCallExpr(Call expr);

        T visitGetExpr(Get expr);

        T visitSetExpr(Set expr);

        T visitThisExpr(This expr);

        T visitSuperExpr(Super expr);

        T visitGroupingExpr(Grouping expr);

        T visitLiteralExpr(Literal expr);

        T visitLogicalExpr(Logical expr);

        T visitUnaryExpr(Unary expr);

        T visitVariableExpr(Variable expr);

        T visitAssignExpr(Assign expr);
    }

    static class Binary extends Expr {
        Binary(Expr left, Token operator, Expr right) {
            this.left = left;
            this.operator = operator;
            this.right = right;
        }

        @Override
        <T> T accept(Visitor<T> visitor) {
            return visitor.visitBinaryExpr(this);
        }

        final Expr left;
        final Token operator;
        final Expr right;
    }

    static class Call extends Expr {
        Call(Expr callee, Token paren, List<Expr> arguments) {
            this.callee = callee;
            this.paren = paren;
            this.arguments = arguments;
        }

        @Override
        <T> T accept(Visitor<T> visitor) {
            return visitor.visitCallExpr(this);
        }

        final Expr callee;
        final Token paren;
        final List<Expr> arguments;
    }

    static class Get extends Expr {
        Get(Expr object, Token name) {
            this.object = object;
            this.name = name;
        }

        @Override
        <T> T accept(Visitor<T> visitor) {
            return visitor.visitGetExpr(this);
        }

        final Expr object;
        final Token name;
    }

    static class Set extends Expr {
        Set(Expr object, Token name, Expr value) {
            this.object = object;
            this.name = name;
            this.value = value;
        }

        @Override
        <T> T accept(Visitor<T> visitor) {
            return visitor.visitSetExpr(this);
        }

        final Expr object;
        final Token name;
        final Expr value;
    }

    static class This extends Expr {
        This(Token keyword) {
            this.keyword = keyword;
        }

        @Override
        <T> T accept(Visitor<T> visitor) {
            return visitor.visitThisExpr(this);
        }

        final Token keyword;
    }

    static class Super extends Expr {
        Super(Token keyword, Token method) {
            this.keyword = keyword;
            this.method = method;
        }

        @Override
        <T> T accept(Visitor<T> visitor) {
            return visitor.visitSuperExpr(this);
        }

        final Token keyword;
        final Token method;
    }

    static class Grouping extends Expr {
        Grouping(Expr expression) {
            this.expression = expression;
        }

        @Override
        <T> T accept(Visitor<T> visitor) {
            return visitor.visitGroupingExpr(this);
        }

        final Expr expression;
    }

    static class Literal extends Expr {
        Literal(Object value) {
            this.value = value;
        }

        @Override
        <T> T accept(Visitor<T> visitor) {
            return visitor.visitLiteralExpr(this);
        }

        final Object value;
    }

    static class Logical extends Expr {
        Logical(Expr left, Token operator, Expr right) {
            this.left = left;
            this.operator = operator;
            this.right = right;
        }

        @Override
        <T> T accept(Visitor<T> visitor) {
            return visitor.visitLogicalExpr(this);
        }

        final Expr left;
        final Token operator;
        final Expr right;
    }

    static class Unary extends Expr {
        Unary(Token operator, Expr right) {
            this.operator = operator;
            this.right = right;
        }

        @Override
        <T> T accept(Visitor<T> visitor) {
            return visitor.visitUnaryExpr(this);
        }

        final Token operator;
        final Expr right;
    }

    static class Variable extends Expr {
        Variable(Token name) {
            this.name = name;
        }

        @Override
        <T> T accept(Visitor<T> visitor) {
            return visitor.visitVariableExpr(this);
        }

        final Token name;
    }

    static class Assign extends Expr {
        Assign(Token name, Expr value) {
            this.name = name;
            this.value = value;
        }

        @Override
        <T> T accept(Visitor<T> visitor) {
            return visitor.visitAssignExpr(this);
        }

        final Token name;
        final Expr value;
    }

    abstract <T> T accept(Visitor<T> visitor);
}
