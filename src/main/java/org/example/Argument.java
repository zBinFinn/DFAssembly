package org.example;

import java.util.Vector;

public class Argument {
    Type type;
    String value;
    String scope;
    String[] vector = new String[3];
    String[] location = new String[5];
    String[] sound;
    String[] particle = new String[2];

    private String parseFromUntil (String input, String start, String end) {
        if (!input.endsWith(end)) {
            ErrUtil.sendError(input + " did not end with " + end);
            return "ERROR: Input did not end with expected end";
        }
        input = input.replaceFirst(start, "");

        StringBuilder out = new StringBuilder();
        for (int i = 0; i < input.length() - end.length(); i++) {
            out.append(input.charAt(i));
        }

        return out.toString();
    }

    public Argument (String arg) {
        // Explicitly typed arguments
        // Number
        if (arg.startsWith("num[")) {
            type = Type.NUMBER;
            value = parseFromUntil(arg, "num\\[", "]");
            return;
        }
        // String
        if (arg.startsWith("str[")) {
            type = Type.STRING;
            value = parseFromUntil(arg, "str\\[", "]");
            return;
        }
        // Text
        if (arg.startsWith("txt[")) {
            type = Type.TEXT;
            value = parseFromUntil(arg, "txt\\[", "]");
            return;
        }
        // Vector
        if (arg.startsWith("vec[")) {
            type = Type.VECTOR;
            value = parseFromUntil(arg, "vec\\[", "]");
            vector = value.split(" ");
            return;
        }
        // Location
        if (arg.startsWith("loc[")) {
            type = Type.LOCATION;
            value = parseFromUntil(arg, "loc\\[", "]");
            location = value.split(" ");
            return;
        }
        // Sound
        if (arg.startsWith("snd[")) {
            type = Type.SOUND;
            value = parseFromUntil(arg, "snd\\[", "]");
            sound = value.split(" ");
            return;
        }
        // Particle
        if (arg.startsWith("part[")) {
            type = Type.PARTICLE;
            value = parseFromUntil(arg, "part\\[", "]");
            particle = value.split(" ");
            particle[1] = particle[1].replaceAll(";", ",");
            return;
        }

        // Variable
        if (arg.startsWith("var[")) {
            type = Type.VARIABLE;
            String parsed = parseFromUntil(arg, "var\\[", "]");

            StringBuilder scopeBuilder = new StringBuilder();
            StringBuilder valueBuilder = new StringBuilder();
            boolean scope = true;
            for (char c : parsed.toCharArray()) {
                if (c == ' ') {
                    if (!scope) {
                        valueBuilder.append(c);
                    }
                    scope = false;
                } else {
                    if (scope) {
                        scopeBuilder.append(c);
                    } else {
                        valueBuilder.append(c);
                    }
                }
            }

            value = valueBuilder.toString();
            this.scope = scopeBuilder.toString();
            return;
        }






        // Implicitly typed arguments
        // Number
        if (CharHelper.numeric(arg.charAt(0))) {
            type = Type.NUMBER;
            value = arg;
            return;
        }
        // String
        if (arg.charAt(0) == '"') {
            type = Type.STRING;
            value = arg.replaceAll("\"", "");
            return;
        }
        // Text
        if (arg.charAt(0) == '\'') {
            type = Type.TEXT;
            value = arg.replaceAll("'", "");
            return;
        }
        // Variable
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
        // Vector
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
        return switch (scope) {
            case "LOCAL" -> "local";
            case "GAME" -> "unsaved";
            case "SAVE" -> "saved";
            default -> "line";
        };
    }

    public String toString(int slot) {
        return "{\"item\":{\"id\":\"" + type.val + "\",\"data\":{" + getData() + "}},\"slot\":" + slot + "}";
        /*
        if (type == Type.VARIABLE) {
            return "{\"item\":{\"id\":\"" + type.val + "\",\"data\":{\"name\":\"" + value + "\",\"scope\":\"" + getScope() + "\"}},\"slot\":" + slot + "}";
        }
        if (type == Type.VECTOR) {
            return "{\"item\":{\"id\":\"" + type.val + "\",\"data\":{\"x\":" + vector[0] + ",\"y\":" + vector[1] + ",\"z\":" + vector[2] + "}},\"slot\":" + slot + "}";
        }
        if (type == Type.LOCATION) {
            return "{\"item\":{\"id\":\"" + type.val + "\",\"data\":{\"isBlock\":" + "false" + ",\"loc\":{\"x\":" + location[0] + ",\"y\":" + location[1] + ",\"z\":" + location[2] + ",\"pitch\":" + location[3] + ",\"yaw\":" + location[4] + "}}},\"slot\":" + slot + "}";
        }
        return "{\"item\":{\"id\":\"" + type.val + "\",\"data\":{\"name\":\"" + value + "\"}},\"slot\":" + slot + "}"; */
    }

    private String getData() {
        return switch (type) {
            case VARIABLE -> "\"name\":\"" + value + "\",\"scope\":\"" + getScope() + "\"";
            case STRING, TEXT, NUMBER -> "\"name\":\"" + value + "\"";
            case VECTOR -> "\"x\":" + vector[0] + ",\"y\":" + vector[1] + ",\"z\":" + vector[2];
            case LOCATION -> "\"isBlock\":" + "false" + ",\"loc\":{\"x\":" + location[0] + ",\"y\":" + location[1] + ",\"z\":" + location[2] + ",\"pitch\":" + location[3] + ",\"yaw\":" + location[4] + "}";
            case SOUND -> "\"pitch\":" + sound[0] + ",\"vol\":" + sound[1] + ",\"sound\":\"" + sound[2] + "\"" + ((sound.length == 3) ? "" : ",\"variant\":\"" + sound[3] + "\"");
            case PARTICLE -> "\"particle\":\"" + particle[0] + "\",\"cluster\":{" + particle[1] + "}";
            default -> "NULL";
        };


    }

//{"items":[{"item":{"id":"num","data":{"name":"2"}},"slot":0},{"item":{"id":"num","data":{"name":"15"}},"slot":1}]}




    private enum Type {
        STRING ("txt"),
        NUMBER ("num"),
        TEXT ("comp"),
        VARIABLE("var"),
        VECTOR ("vec"),
        LOCATION ("loc"),
        PARTICLE("part"),
        SOUND ("snd");

        final String val;
        Type (String s) {
            val = s;
        }
    }
}
