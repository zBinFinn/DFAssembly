package org.example;

import org.example.token.*;

import java.util.ArrayList;
import java.util.Optional;

public class Parser {
    private ArrayList<Token> tokens;
    private int index;


    public Parser (ArrayList<Token> tokens) {
        this.tokens = tokens;
        index = 0;
    }

    public String parse() {
        StringBuilder parsed = new StringBuilder("/dfgive ender_chest[minecraft:custom_data={PublicBukkitValues:{\"hypercube:codetemplatedata\":\"" +
                                                    "{\\\"author\\\":\\\"zBinFinn\\\",\\\"name\\\":\\\"§e§lEvent §6 » §ePlayer Join Game Event\\\",\\\"version\\\":1,\\\"code\\\":\\\"");
                StringBuilder data = new StringBuilder("{\"blocks\":[");
        boolean addCommas = false;
        while (peek().isPresent()) {
            if (peek().get() instanceof ActionToken) {
                if (addCommas) {
                    data.append(",");
                }
                ActionToken action = (ActionToken) consume();
                CodeBlock codeBlock = new CodeBlock(action.type);


                if (!(codeBlock.getID().equals("bracket"))) {
                    if (peek().isPresent() && peek().get() instanceof StringLiteralToken) {
                        StringLiteralToken stringLit = (StringLiteralToken) consume();
                        codeBlock.setAction(stringLit.value);
                    } else {
                        return "ERROR: StringLiteral isnt a string literal or doesnt exist oopsie";
                    }
                }

                if (peek().isPresent() && peek().get() instanceof ArgumentsToken) {
                    ArgumentsToken argumentsToken = (ArgumentsToken) consume();
                    codeBlock.setArgs(argumentsToken.args);
                }

                if (peek().isPresent() && peek().get() instanceof SemicolonToken) {
                    data.append(codeBlock.toString());
                    addCommas = true;
                    consume();
                }
            } else {
                return "ERROR: Didn't start line with Action";
            }


        }
        data.append("]}");

        // {"id":"block","block":"event","args":{"items":[]},"action":"Join"}

        // {"blocks":[{"id":"block","block":"event","args":{"items":[]},"action":"Join"}]}


        // H4sIAAAAAAAA/6tWSsrJT84uVrKKrlbKTFGygvCVdKC0lVJqWWpeCZCfWJQOVAVUVJKaC1IeWwsUSy7JzM8DKvLKz8xTqo2tBQDoiiunTwAAAA==

        System.out.println(data.toString());

        try {
            parsed.append(Compressor.compress(data.toString()));
        } catch (Exception e) {
            ErrUtil.sendError(e.getMessage());
        }




        parsed.append("\\\"}\"}}] 1");



        return parsed.toString();

    }


    private Token consume() {
        return tokens.get(index++);
    }

    private Optional<Token> peek() {
        return peek(0);
    }
    private Optional<Token> peek(int ahead) {
        if ((index + ahead) >= tokens.size()) {
            return Optional.empty();
        }

        return Optional.of(tokens.get(index + ahead));
    }

}
