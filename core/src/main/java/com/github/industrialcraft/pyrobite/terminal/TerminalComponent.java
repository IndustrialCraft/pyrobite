package com.github.industrialcraft.pyrobite.terminal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.github.industrialcraft.pyrobite.input.InputManager;
import com.github.industrialcraft.pyrobite.ui.UIComponent;

import java.util.ArrayList;

public class TerminalComponent extends UIComponent {

    private String content;
    private int backspaceTicks;
    private int backspaceRemoveTicks;

    private final ArrayList<String> messages;
    private final TerminalExecutor executor;
    private final TerminalInput input;
    private final BitmapFont font;


    public TerminalComponent() {
        super();

        content = "";

        messages = new ArrayList<>();
        executor = new TerminalExecutor();
        input = new TerminalInput(this);
        font = new BitmapFont(Gdx.files.internal("terminal/terminal_font.fnt"));

        messages.add("|Pyrobite development console.    ");
        messages.add("|IndustrialCraft Studios (C) 2022 ");
        messages.add("");

        InputManager.addInput(input);
    }

    public void charPressed(char character) {
        content = (content + character).replaceAll("\\p{C}", "");
    }

    @Override
    public void render(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer, Camera camera) {
        font.setColor(Color.WHITE);

        if (input.isBackspaceHeldDown)
            backspaceTicks ++;
        else
            backspaceTicks = 0;

        if (backspaceTicks > 10) {
            backspaceRemoveTicks ++;

            if (backspaceRemoveTicks > 1) {
                backspaceRemoveTicks = 0;
                backspace();
            }

        }

        int index = 2;
        for (int i = 0; i != messages.size(); i++) {
            String message = messages.get(messages.size()-1 - i);

            if (index > 40) {
                font.draw(spriteBatch, "...", 40, 40 + index * font.getLineHeight());
                break;
            }

            font.draw(spriteBatch, message, 40, 40 + index * font.getLineHeight());
            index ++;
        }

        font.draw(spriteBatch, "pyrobite_Engine: / " + content, 40, 40);

    }

    public void shiftString(String s) {
        messages.add(s);
    }

    public void submit() {
        messages.add("/ " + content);
        executor.execute(this, content);
        content = "";
    }

    public void backspace() {
        if (content.length() >= 1)
            content = content.substring(0, content.length()-1);
    }
}
