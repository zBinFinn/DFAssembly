package org.example;

import java.util.Arrays;

public class CodeBlock {
    private String action;
    private Arguments args = new Arguments();
    private Type type; //i.e. "PLAYER_ACTION"

    public CodeBlock (String type) {
        try {
            this.type = Type.valueOf(type);
        } catch (Exception e) {
            ErrUtil.sendError("Codeblock not recognized");
        }

    }

    private String getArgs() {
        return "0";
    }

    public CodeBlock () {

    }


    public String toString() {
        if (type.id.equals("block")) {
            return "{\"id\":\"" + type.id +"\",\"block\":\"" + type.block + "\",\"args\":{" + args.toString() + "},\"" + ((type.hasData) ? "data" : "action") + "\":\"" + action + "\"}";
        }



        if (type.id.equals("bracket")) {
            return "{\"id\":\"" + type.id +"\",\"direct\":\"" + type.direction + "\",\"type\":\"" + type.bracket + "\"}";
        }
        return "null";
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setArgs(String args) {
        this.args = new Arguments(args);
    }

    public String getID() {
        return type.id;
    }

    private enum Type {
        PLAYER_EVENT("block", "event"),
        FUNCTION ("block", "func", true),
        PROCESS("block", "process"),

        CALL_FUNCTION("block", "call_func", true),
        PLAYER_ACTION("block", "player_action"),
        SET_VARIABLE("block", "set_var"),
        CONTROL("block", "control"),


        IF_PLAYER("block", "if_player"),
        OPEN_BRACKET("bracket", "open", "norm"),
        CLOSE_BRACKET("bracket", "close", "norm"),

        REPEAT("block", "repeat"),
        OPEN_BRACKET_REPEAT("bracket", "open", "sticky"),
        CLOSE_BRACKET_REPEAT("bracket", "close", "sticky");


        String id;
        String block;
        String bracket;
        String direction;
        boolean hasData = false;

        Type (String id, String block) {
            this.id = id;
            this.block = block;
        }

        Type (String id, String direction, String bracket) {
            this.id = id;
            this.direction = direction;
            this.bracket = bracket;
        }

        Type (String id, String block, boolean hasData) {
            this.id = id;
            this.block = block;
            this.hasData = hasData;
        }
    }
}
