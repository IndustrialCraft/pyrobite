package com.github.industrialcraft.pyrobite.terminal;

public class TerminalExecutor {

    public enum ConsoleCommands {
        HELP("help", 0),
        SAVE("save", 1),
        LOAD("load", 2);

        private final String command;
        private final int args;

        ConsoleCommands(String cmd, int args) {
            this.command = cmd;
            this.args = args;
        }

    }

    public void execute(TerminalComponent c, String terminalLine) {
        try {
            String cmd = terminalLine.split(" ")[0];

            for (ConsoleCommands command : ConsoleCommands.values()) {
                if (command.command.equals(cmd)) {
                    String[] args = new String[command.args];

                    int index = -2;
                    for (String argument : terminalLine.split(" ")) {
                        index ++;

                        if (index == -1) {
                            continue;
                        }

                        args[index] = argument;
                    }

                    executeCommand(c, command, args);
                    break;
                }
            }
        }
        catch (Exception e) {
            c.shiftString("Command execution failed ...");
            c.shiftString("Please use `help`");
            e.printStackTrace();
        }
    }

    public void executeCommand(TerminalComponent c, ConsoleCommands command, String[] args) {

        switch (command) {
            case HELP: {
                c.shiftString("HELP: fasfasfs");
                break;
            }

            case LOAD: {
                c.shiftString("LOAD: Loading map: " + args[0]);
                break;
            }
        }
    }

}
