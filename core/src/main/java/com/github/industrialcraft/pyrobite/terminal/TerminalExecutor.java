package com.github.industrialcraft.pyrobite.terminal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.github.industrialcraft.pyrobite.entity.Entity;
import com.github.industrialcraft.pyrobite.scene.Scene;
import com.github.industrialcraft.pyrobite.scene.SceneSaverLoader;
import com.google.gson.JsonParser;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static com.mojang.brigadier.arguments.FloatArgumentType.floatArg;
import static com.mojang.brigadier.arguments.FloatArgumentType.getFloat;
import static com.mojang.brigadier.arguments.StringArgumentType.getString;
import static com.mojang.brigadier.arguments.StringArgumentType.string;

public class TerminalExecutor {

    public static OrthographicCamera commandCamera;

    public static class CommandSourceData {
        public Scene scene;
        public TerminalComponent terminal;

        public CommandSourceData(Scene scene, TerminalComponent terminal) {
            this.scene = scene;
            this.terminal = terminal;
        }
    }

    public enum ConsoleCommands {
        HELP("help", "Shows command help menu"),
        SAVE("save", "Saves map to json file"),
        LOAD("load", "Load map from json file"),
        EXIT("exit", "Closes game"),
        CLEAR("clear", "Clears terminal"),
        SDET("sdet", "Prints scene details"),
        UDET("udet", "UI Details"),
        NCAM("nuicam", "Creates new UI camera"),
        FPS ("fps", "Show frames per second");

        public final String command;
        public final String desc;
        ConsoleCommands(String cmd, String desc) {
            this.command = cmd;
            this.desc = desc;
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
            context.getSource().terminal.shiftString("SDET:   EntityCount: " + Scene.getInstance().getEntities().size());

            for (Entity entity : Scene.getInstance().getEntities()) {
                context.getSource().terminal.shiftString("SDET:      EntityEntry: " + entity.toJson());
            }
            return 1;
        }));

        /*
            Load command
         */
        this.dispatcher.register(LiteralArgumentBuilder.<CommandSourceData>literal(ConsoleCommands.LOAD.command).then(RequiredArgumentBuilder.<CommandSourceData,String>argument("file", string()).executes(context -> {
            context.getSource().terminal.shiftString("LOAD: Loading map: " + getString(context, "file"));
            SceneSaverLoader.load(JsonParser.parseString(Gdx.files.internal(getString(context, "file")).readString()).getAsJsonObject());
            context.getSource().terminal.shiftString("LOAD: Loading passed.");
            return 1;
        })));

        /*
            NewCam command
         */
        this.dispatcher.register(LiteralArgumentBuilder.<CommandSourceData>literal(ConsoleCommands.NCAM.command)
                .then(RequiredArgumentBuilder.<CommandSourceData,Float>argument("width", floatArg())
                .then(RequiredArgumentBuilder.<CommandSourceData,Float>argument("height", floatArg())
                .then(RequiredArgumentBuilder.<CommandSourceData,Float>argument("x", floatArg())
                .then(RequiredArgumentBuilder.<CommandSourceData,Float>argument("y", floatArg())
                .executes(context -> {

                    float width = getFloat(context,"width");
                    float height = getFloat(context,"height");
                    float x = getFloat(context, "x");
                    float y= getFloat(context, "y");

                    context.getSource().terminal.shiftString("NCAM: Creating new camera..");

                    commandCamera = new OrthographicCamera(width, height);
                    commandCamera.position.set(x, y, 0);


                    context.getSource().terminal.shiftString("NCAM: New camera was assigned.");
                    return 1;
        }))))));

        /*
            Help command,
            TODO: Update
         */
        this.dispatcher.register(LiteralArgumentBuilder.<CommandSourceData>literal(ConsoleCommands.HELP.command).then(RequiredArgumentBuilder.<CommandSourceData,String>argument("command",string()).executes(context -> {
            for (ConsoleCommands command_1 : ConsoleCommands.values()) {
                if(command_1.command.equals(getString(context, "command"))) {
                    context.getSource().terminal.shiftString("HELP: " + command_1.command + " - " + command_1.desc);
                    ArrayList<String> usages = new ArrayList<>();
                    dispatcher.getSmartUsage(dispatcher.getRoot(), context.getSource()).forEach((commandSourceDataCommandNode, s) -> {
                        if(commandSourceDataCommandNode.getName().equals(command_1.command))
                            usages.add(s);
                    });
                    for(String usage : usages){
                        context.getSource().terminal.shiftString("USAGE: " + usage);
                    }
                    break;
                }
            }
            return 1;
        })).executes(context -> {
            for (ConsoleCommands command_1 : ConsoleCommands.values()) {
                context.getSource().terminal.shiftString("HELP: " + command_1.command + " - " + command_1.desc);
            }
            return 1;
        }));

    }
    public List<String> showAutocomplete(TerminalComponent c, String input){
        final ParseResults<CommandSourceData> parse = dispatcher.parse(input, new CommandSourceData(Scene.getInstance(), c));

        CompletableFuture<Suggestions> suggestions = dispatcher.getCompletionSuggestions(parse);
        return suggestions.join().getList().stream().map(suggestion -> suggestion.getText()).collect(Collectors.toList());
    }
    public void execute(TerminalComponent c, String terminalLine) {
        final ParseResults<CommandSourceData> parse = dispatcher.parse(terminalLine, new CommandSourceData(Scene.getInstance(), c));

        try {
            final int result = dispatcher.execute(parse);
        } catch (CommandSyntaxException | RuntimeException e) {
            if (e.getMessage() == null) {
                c.shiftString("Command execution failed, no further information! " + e);
                return;
            }
            System.err.println(e.getMessage());
            c.shiftString(e.getMessage());
        }
    }
}
