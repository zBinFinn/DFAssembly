package org.example.token;

public class ActionToken implements Token{
    public String type = "NULL";

    public ActionToken(String type) {
        this.type = type;
    }

    @Override
    public String getName() {
        return "action";
    }

    @Override
    public String toString() {
        return "action (" + type + ")";
    }
}
