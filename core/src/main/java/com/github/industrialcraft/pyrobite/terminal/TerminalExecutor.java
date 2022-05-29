package com.github.industrialcraft.pyrobite.terminal;

import com.badlogic.gdx.Gdx;
import com.github.industrialcraft.pyrobite.PyrobiteMain;
import com.github.industrialcraft.pyrobite.entity.Entity;
import com.github.industrialcraft.pyrobite.scene.Scene;
import com.github.industrialcraft.pyrobite.scene.SceneSaverLoader;
import com.google.gson.JsonParser;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import static com.mojang.brigadier.arguments.StringArgumentType.getString;
import static com.mojang.brigadier.arguments.StringArgumentType.string;

public class TerminalExecutor {

    public static class CommandSourceData {
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

        /*
         * FPS command
         */
        this.dispatcher.register(LiteralArgumentBuilder.<CommandSourceData>literal(ConsoleCommands.FPS.command).executes(context -> {
            context.getSource().terminal.shiftString("FPS: Your frame-rate is: " + Gdx.graphics.getFramesPerSecond());
            return 1;
        }));

        /*
            Exit command
         */
        this.dispatcher.register(LiteralArgumentBuilder.<CommandSourceData>literal(ConsoleCommands.EXIT.command).executes(context -> {
            Gdx.app.exit();
            return 1;
        }));

        /*
            Clear command
         */
        this.dispatcher.register(LiteralArgumentBuilder.<CommandSourceData>literal(ConsoleCommands.CLEAR.command).executes(context -> {
            context.getSource().terminal.clear();
            return 1;
        }));

        /*
            Scene details command
         */
        this.dispatcher.register(LiteralArgumentBuilder.<CommandSourceData>literal(ConsoleCommands.SDET.command).executes(context -> {
            context.getSource().terminal.shiftString("SDET: SceneDetails");
            context.getSource().terminal.shiftString("SDET:   EntityCount: " + PyrobiteMain.getScene().getEntities().size());

            for (Entity entity : PyrobiteMain.getScene().getEntities()) {
                context.getSource().terminal.shiftString("SDET:      EntityEntry: " + entity.toJson());
            }
            return 1;
        }));

        /*
            Load command
         */
        this.dispatcher.register(LiteralArgumentBuilder.<CommandSourceData>literal(ConsoleCommands.LOAD.command).then(RequiredArgumentBuilder.<CommandSourceData,String>argument("file", string()).executes(context -> {
            context.getSource().terminal.shiftString("LOAD: Loading map: " + getString(context, "file"));
            PyrobiteMain.setScene(SceneSaverLoader.load(JsonParser.parseString(Gdx.files.internal(getString(context, "file")).readString()).getAsJsonObject()));
            context.getSource().terminal.shiftString("LOAD: Loading passed.");
            return 1;
        })));

        /*
            Help command,
            TODO: Update
         */
        this.dispatcher.register(LiteralArgumentBuilder.<CommandSourceData>literal(ConsoleCommands.HELP.command).executes(context -> {
            context.getSource().terminal.shiftString("HELP: Help menu");
            for (ConsoleCommands command_1 : ConsoleCommands.values()) {
                context.getSource().terminal.shiftString("HELP: " + command_1.command + ": args[" + command_1.args + "] " + command_1.longerName);
            }
            return 1;
        }));

    }

    public void execute(TerminalComponent c, String terminalLine) {
        final ParseResults<CommandSourceData> parse = dispatcher.parse(terminalLine, new CommandSourceData(PyrobiteMain.getScene(), c));

        try {
            final int result = dispatcher.execute(parse);
        } catch (CommandSyntaxException | RuntimeException e) {
            if (e.getMessage() == null) {
                c.shiftString("Command execution failed, no further information! " + e);
                return;
            }
            c.shiftString(e.getMessage());
        }
    }
}
