package org.example.token;

public class StringLiteralToken implements Token {
    public String value = "";

    public StringLiteralToken(String value) {
        this.value = value;
    }

    @Override
    public String getName() {
        return "string_literal";
    }


    @Override
    public String toString() {
        return "string_literal (" + value + ")";
    }
}
