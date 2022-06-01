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
import java.util.List;

public class TerminalComponent extends UIComponent {

    public static BitmapFont font;

    private String content;
    private int backspaceTicks;
    private int backspaceRemoveTicks;

    private final ArrayList<String> messages;
    private final TerminalExecutor executor;
    private final TerminalInput input;
    private boolean active;
    private List<String> suggestions;


    public TerminalComponent() {
        super();
        this.active = false;



        messages = new ArrayList<>();
        executor = new TerminalExecutor();
        input = new TerminalInput(this);

        font = new BitmapFont(Gdx.files.internal("terminal/terminal_font.fnt"));
        //font.getData().setScale(3);

        content = "";
        this.suggestions = new ArrayList<>();

        messages.add("|Pyrobite development console.    ");
        messages.add("|IndustrialCraft Studios (C) 2022 ");
        messages.add("");

        InputManager.addInput(input);
    }
    public void updateSuggestions(){
        if(content.isEmpty()){
            this.suggestions.clear();
            return;
        }
        this.suggestions = executor.showAutocomplete(this, content);
        System.out.println(suggestions);
    }
    public void charPressed(char character) {
        if(character=='`'){
            active=!active;
            return;
        }
        if(!active)
            return;
        content = (content + character).replaceAll("\\p{C}", "");
        updateSuggestions();
    }

    @Override
    public void render(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer, Camera camera) {
        if(!active)
            return;
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
                font.draw(spriteBatch, "...", 10, 30 + ((index+2) * font.getLineHeight()));
                break;
            }

            if (message == null) {
                message = "(!!) Warning null-message NULL";
            }

            font.draw(spriteBatch, message, 10, 50 + ((index+1) * font.getLineHeight()));
            index ++;
        }

        font.draw(spriteBatch, "pyrobite_Engine: / " + content, 10, 50);

    }

    public void shiftString(String s) {
        messages.add(s);
    }

    public void submit() {
        messages.add("/ " + content);
        executor.execute(this, content);
        content = "";
        updateSuggestions();
    }

    public void backspace() {
        if (content.length() >= 1)
            content = content.substring(0, content.length()-1);
        updateSuggestions();
    }

    public void clear() {
        messages.clear();
    }
}
