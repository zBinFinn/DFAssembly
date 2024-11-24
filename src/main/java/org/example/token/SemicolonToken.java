package org.example.token;

public class SemicolonToken implements Token {

    public SemicolonToken() {

    }

    @Override
    public String getName() {
        return "semicolon";
    }


    @Override
    public String toString() {
        return "semicolon";
    }
}
