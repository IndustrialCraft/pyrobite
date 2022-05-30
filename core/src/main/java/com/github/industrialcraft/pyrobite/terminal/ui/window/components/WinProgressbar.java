package com.github.industrialcraft.pyrobite.terminal.ui.window.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.github.industrialcraft.pyrobite.terminal.ui.window.WinUIComponent;

public class WinProgressbar extends WinUIComponent {

    private int percentage;

    public WinProgressbar(float positionX, float positionY, float width) {
        super(positionX, positionY, width, 100);
    }

    @Override
    public void render(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer, Camera camera) {

    }

    @Override
    public void clickedComponent(int y, int x) {

    }

}
