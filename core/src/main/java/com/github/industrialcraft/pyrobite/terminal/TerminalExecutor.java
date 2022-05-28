package com.github.industrialcraft.pyrobite.terminal;

import com.badlogic.gdx.Gdx;
import com.github.industrialcraft.pyrobite.PyrobiteMain;
import com.github.industrialcraft.pyrobite.scene.Scene;
import com.github.industrialcraft.pyrobite.scene.SceneSaverLoader;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class TerminalExecutor {

    public enum ConsoleCommands {
        HELP("help", 0),
        SAVE("save", 1),
        LOAD("load", 1),
        EXIT("exit", 0),
        FPS ("fps", 0);

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
            c.shiftString(e.getMessage());
            c.shiftString("-----");
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
                PyrobiteMain.setScene(SceneSaverLoader.load(JsonParser.parseString(Gdx.files.internal(args[0]).readString()).getAsJsonObject()));
                c.shiftString("LOAD: Loading passed.");
                break;
            }

            case FPS: {
                c.shiftString("FPS: Your frame-rate is: " + Gdx.graphics.getFramesPerSecond());
                break;
            }

            case EXIT: {
                c.shiftString("EXIT: Hard engine termination");
                Gdx.app.exit();
            }

            default:
                break;
        }
    }

}
