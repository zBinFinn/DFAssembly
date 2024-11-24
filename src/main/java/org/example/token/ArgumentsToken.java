package org.example.token;

public class ArgumentsToken implements Token {
    public String args;

    public ArgumentsToken(String args) {
        this.args = args;
    }

    @Override
    public String getName() {
        return "Arguments";
    }

    public String toString() {
        return "arguments (" + args + ")";
    }
}
