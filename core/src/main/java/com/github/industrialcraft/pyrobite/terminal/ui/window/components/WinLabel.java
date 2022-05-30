package com.github.industrialcraft.pyrobite.terminal.ui.window.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import com.github.industrialcraft.pyrobite.terminal.ui.window.WinUIComponent;

public class WinLabel extends WinUIComponent {

    private final BitmapFont font;
    private final String content;

    public WinLabel(String content, float positionX, float positionY) {
        super(positionX, positionY, 0, 0);

        font = new BitmapFont(Gdx.files.internal("terminal/terminal_font.fnt"));
        font.getData().setScale(3);

        this.content = content;
    }

    public WinLabel(String content, float positionX, float positionY, float size) {
        super(positionX, positionY, 0, 0);

        font = new BitmapFont(Gdx.files.internal("terminal/terminal_font.fnt"));
        font.getData().setScale(size);

        this.content = content;
    }

    public String getContent() {
        return content;
    }

    @Override
    public void render(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer, Camera camera) {
        font.draw(spriteBatch, content, getWindowUpdatedPosX(), getWindowUpdatedPosY());
    }

    @Override
    public void clickedComponent(int y, int x) {

    }

}
