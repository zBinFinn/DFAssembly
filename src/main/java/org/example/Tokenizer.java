package org.example;

import org.example.token.*;

import java.util.ArrayList;
import java.util.Optional;

public class Tokenizer {
    private String code;
    private int index;

    public Tokenizer(String code) {
        index = 0;
        this.code = code;
    }

    public ArrayList<Token> tokenize() {
        StringBuilder buffer = new StringBuilder();
        ArrayList<Token> tokens = new ArrayList<>();
        while (peek().isPresent()) {
            if (CharHelper.alphabetic(peek().get()) && CharHelper.uppercase(peek().get())) {
                buffer.append(consume());
                while (peek().isPresent() && (peek().get() == '_' || (CharHelper.alphabetic(peek().get())) && CharHelper.uppercase(peek().get()))) {
                    buffer.append(consume());
                }
                tokens.add(new ActionToken(buffer.toString()));
                buffer = new StringBuilder();
            } else if (peek().get() == ';') {
                tokens.add(new SemicolonToken());
                consume();
            } else if (CharHelper.whitespace(peek().get())) {
                consume();
            } else if (peek().get() == '"') {
                consume();
                while (peek().isPresent() && peek().get() != '"') {
                    buffer.append(consume());
                }
                consume();
                tokens.add(new StringLiteralToken(buffer.toString()));
                buffer = new StringBuilder();
            } else if (peek().get() == '(') {
                consume();
                while (peek().isPresent() && peek().get() != ')') {
                    buffer.append(consume());
                }
                consume();
                tokens.add(new ArgumentsToken(buffer.toString()));
                buffer = new StringBuilder();


            } else {
                ErrUtil.sendError("Something went horribly wrong");
                break;
            }
        }

        for (Token t : tokens) {
            System.out.println(t);
        }

        return tokens;
    }

    private char consume() {
        char out = code.charAt(index);
        index++;
        return out;
    }

    private Optional<Character> peek() {
        return peek(0);
    }
    private Optional<Character> peek(int ahead) {
        if ((index + ahead) >= code.length()) {
            return Optional.empty();
        }

        return Optional.of(code.charAt(index + ahead));
    }
}
