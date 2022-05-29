package com.github.industrialcraft.pyrobite.terminal;

import com.badlogic.gdx.Gdx;
import com.github.industrialcraft.pyrobite.PyrobiteMain;
import com.github.industrialcraft.pyrobite.entity.Entity;
import com.github.industrialcraft.pyrobite.scene.Scene;
import com.github.industrialcraft.pyrobite.scene.SceneSaverLoader;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import static com.mojang.brigadier.arguments.StringArgumentType.getString;
import static com.mojang.brigadier.arguments.StringArgumentType.string;
import static com.mojang.brigadier.builder.RequiredArgumentBuilder.argument;

public class TerminalExecutor {
    public static class CommandSourceData{
        public Scene scene;
        public TerminalComponent terminal;
        public CommandSourceData(Scene scene, TerminalComponent terminal) {
            this.scene = scene;
            this.terminal = terminal;
        }
    }
    public enum ConsoleCommands {
        HELP("help", 0, "HelpMenu"),
        SAVE("save", 1, "SaveMap"),
        LOAD("load", 1, "LoadMap"),
        EXIT("exit", 0, "ExitEngine"),
        CDET("cdet", 0, "CameraDetails"),
        CLEAR("clear", 0, "ClearTerminal"),
        SDET("sdet", 0, "SceneDetails"),
        FPS ("fps", 0,"FrameRateInfo");

        private final String command;
        private final int args;
        private final String longerName;

        ConsoleCommands(String cmd, int args, String longerName) {
            this.command = cmd;
            this.args = args;
            this.longerName = longerName;

        }

    }

    private CommandDispatcher<CommandSourceData> dispatcher;
    public TerminalExecutor() {
        this.dispatcher = new CommandDispatcher<>();
        this.dispatcher.register(LiteralArgumentBuilder.<CommandSourceData>literal("fps").executes(context -> {
            context.getSource().terminal.shiftString("FPS: Your frame-rate is: " + Gdx.graphics.getFramesPerSecond());
            return 1;
        }));
        this.dispatcher.register(LiteralArgumentBuilder.<CommandSourceData>literal("exit").executes(context -> {
            Gdx.app.exit();
            return 1;
        }));
        this.dispatcher.register(LiteralArgumentBuilder.<CommandSourceData>literal("clear").executes(context -> {
            context.getSource().terminal.clear();
            return 1;
        }));
        this.dispatcher.register(LiteralArgumentBuilder.<CommandSourceData>literal("sdet").executes(context -> {
            context.getSource().terminal.shiftString("SDET: SceneDetails");
            context.getSource().terminal.shiftString("SDET:   EntityCount: " + PyrobiteMain.getScene().getEntities().size());

            for (Entity entity : PyrobiteMain.getScene().getEntities()) {
                context.getSource().terminal.shiftString("SDET:      EntityEntry: " + entity.toJson());
            }
            return 1;
        }));
        this.dispatcher.register(LiteralArgumentBuilder.<CommandSourceData>literal("load").then(RequiredArgumentBuilder.<CommandSourceData,String>argument("file", string()).executes(context -> {
            context.getSource().terminal.shiftString("LOAD: Loading map: " + getString(context, "file"));
            PyrobiteMain.setScene(SceneSaverLoader.load(JsonParser.parseString(Gdx.files.internal(getString(context, "file")).readString()).getAsJsonObject()));
            context.getSource().terminal.shiftString("LOAD: Loading passed.");
            return 1;
        })));
    }

    public void execute(TerminalComponent c, String terminalLine) {
        final ParseResults<CommandSourceData> parse = dispatcher.parse(terminalLine, new CommandSourceData(PyrobiteMain.getScene(), c));
        try {
            final int result = dispatcher.execute(parse);
        } catch (CommandSyntaxException e) {
            c.shiftString(e.getMessage());
        } catch (RuntimeException e){
            c.shiftString(e.getMessage());
        }
        /*try {
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

                    for (String el : args)
                        if (el == null)
                            throw new IllegalStateException("Argument was null.");


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
        */
    }

    public void executeCommand(TerminalComponent c, ConsoleCommands command, String[] args) {

        switch (command) {
            case HELP: {
                c.shiftString("HELP: Help menu");
                for (ConsoleCommands command_1 : ConsoleCommands.values()) {
                    c.shiftString("HELP: " + command_1.command + ": args[" + command_1.args + "] " + command_1.longerName);
                }
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

            case CLEAR: {
                c.clear();
                break;
            }

            case CDET: {
                c.shiftString("CDET: CameraDetails:");
                c.shiftString("CDET:   Width: " + "ERROR");
                c.shiftString("CDET:   Height: " + "ERROR");
                c.shiftString("CDET:   PosX: " + "ERROR");
                c.shiftString("CDET:   PosY: " + "ERROR");
                break;
            }

            case SDET: {
                c.shiftString("SDET: SceneDetails");
                c.shiftString("SDET:   EntityCount: " + PyrobiteMain.getScene().getEntities().size());

                for (Entity entity : PyrobiteMain.getScene().getEntities()) {
                    c.shiftString("SDET:      EntityEntry: " + entity.toJson());
                }

                break;
            }

            default:
                break;
        }
    }

}
