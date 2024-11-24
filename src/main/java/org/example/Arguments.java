package org.example;

import java.util.ArrayList;
import java.util.Arrays;

public class Arguments {
    ArrayList<Argument> arguments = new ArrayList<Argument>();
    public String toString() {
        StringBuffer out = new StringBuffer("\"items\":[");

        int slot = 0;
        for (Argument arg : arguments) {
            out.append(arg.toString(slot++));
            if (!(arguments.getLast().equals(arg))) {
                out.append(",");
            }
        }

        out.append("]");
        return out.toString();
    }

    public Arguments() {}
    public Arguments(String args) {
        if (args.isEmpty()) {
            arguments = new ArrayList<>();
            return;
        }

        ArrayList<String> stringArgs = new ArrayList<>();

        args = args.replaceAll(", ", ",");
        if (args.contains(",")) {
            stringArgs.addAll(Arrays.asList(args.split(",")));
        } else {
            stringArgs.add(args);
        }

        arguments = parse(stringArgs);
    }

    private ArrayList<Argument> parse(ArrayList<String> args) {
        ArrayList<Argument> out = new ArrayList<>();

        for (String arg : args) {
            out.add(new Argument(arg));
        }

        return out;
    }

}
