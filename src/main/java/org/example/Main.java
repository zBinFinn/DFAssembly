package org.example;

import org.example.token.Token;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        if (args.length != 1) {
            ErrUtil.sendError("Not exactly one argument given :(");
            return;
        }

        Path path = Path.of(args[0]);
        boolean exists = Files.exists(path);

        if (!exists) {
            ErrUtil.sendError("File doesn't exist");
        }

        String code;

        try {
            code = Files.readString(path);
        } catch (IOException e) {
            ErrUtil.sendError(e.getMessage());
            return;
        }


        System.out.println("CODE: ");
        System.out.println(code);
        System.out.println();

        String[] codeArr;
        if (code.contains("NEW")) {
            codeArr = code.split("NEW");
        } else {
            codeArr = new String[]{code};
        }

        String[] parsedArray = new String[codeArr.length];
        int count = 0;
        for (String c : codeArr) {
            System.out.println("----- Snippet " + count + " -----");
            System.out.println("TOKENS: ");
            Tokenizer tokenizer = new Tokenizer(c);
            ArrayList<Token> tokens = tokenizer.tokenize();
            System.out.println();

            System.out.println("PARSED: ");
            Parser parser = new Parser(tokens);
            String parsed = parser.parse();
            System.out.println(parsed);
            parsedArray[count] = parsed;
            System.out.println();
            count++;
        }


        System.out.println("----- /dfgive commands -----");
        for (int i = 0; i < parsedArray.length; i++) {
            System.out.println(i + ":");
            System.out.println(parsedArray[i]);
        }

    }
}