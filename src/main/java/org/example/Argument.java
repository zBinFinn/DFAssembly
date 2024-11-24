package org.example;

import java.util.Vector;

public class Argument {
    Type type;
    String value;
    String scope;
    String[] vector = new String[3];

    public Argument (String arg) {
        if (CharHelper.numeric(arg.charAt(0))) {
            type = Type.NUMBER;
            value = arg;
            return;
        }
        if (arg.charAt(0) == '"') {
            type = Type.STRING;
            value = arg.replaceAll("\"", "");
            return;
        }
        if (arg.charAt(0) == '\'') {
            type = Type.TEXT;
            value = arg.replaceAll("'", "");
            return;
        }
        if (CharHelper.alphabetic(arg.charAt(0))) {
            type = Type.VARIABLE;
            StringBuilder scopeBuilder = new StringBuilder();
            if (CharHelper.uppercase(arg.charAt(0))) {
                while (CharHelper.uppercase(arg.charAt(0)) && arg.charAt(0) != ' ') {
                    scopeBuilder.append(arg.charAt(0));
                    arg = arg.replaceFirst(arg.charAt(0) + "", "");
                }
                scope = scopeBuilder.toString();
                System.out.println(scope);
            } else {
                scope = "LINE";
            }
            arg = arg.replace(" ", "");
            value = arg;
            return;
        }
        if (arg.charAt(0) == '<') {
            type = Type.VECTOR;
            int charIndex = 1;
            int index = 0;
            StringBuilder val = new StringBuilder();
            while (true) {
                if (arg.charAt(charIndex) == ' ' || arg.charAt(charIndex) == '>') {
                    vector[index] =  val.toString();
                    val = new StringBuilder();
                    index++;
                    if (arg.charAt(charIndex) == '>') {
                        break;
                    }
                } else {
                    val.append(arg.charAt(charIndex));
                }
                charIndex++;
                if (arg.length() <= charIndex) {
                    ErrUtil.sendError("Vector parsing failed, no \">\" found");
                    return;
                }
            }
            return;
        }
    }

    private String getScope() {
        switch (scope) {
            case "LOCAL": return "local";
            case "GAME": return "unsaved";
            case "SAVE": return "saved";
            case "LINE":
            default: return "line";
        }
    }

    public String toString(int slot) {
        if (type == Type.VARIABLE) {
            return "{\"item\":{\"id\":\"" + type.val + "\",\"data\":{\"name\":\"" + value + "\",\"scope\":\"" + getScope() + "\"}},\"slot\":" + slot + "}";
        }
        if (type == Type.VECTOR) {
            return "{\"item\":{\"id\":\"" + type.val + "\",\"data\":{\"x\":" + vector[0] + ",\"y\":" + vector[1] + ",\"z\":" + vector[2] + "}},\"slot\":" + slot + "}";
        }
        return "{\"item\":{\"id\":\"" + type.val + "\",\"data\":{\"name\":\"" + value + "\"}},\"slot\":" + slot + "}";
    }

//{"items":[{"item":{"id":"num","data":{"name":"2"}},"slot":0},{"item":{"id":"num","data":{"name":"15"}},"slot":1}]}




    private enum Type {
        STRING ("txt"),
        NUMBER ("num"),
        TEXT ("comp"),
        VARIABLE("var"),
        VECTOR ("vec");

        final String val;
        Type (String s) {
            val = s;
        }
    }
}
