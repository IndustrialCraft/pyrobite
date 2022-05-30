package com.github.industrialcraft.pyrobite.terminal.ui.window.components;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.github.industrialcraft.pyrobite.terminal.ui.window.WinUIComponent;

public class WinButton extends WinUIComponent {

    public WinButton(float positionX, float positionY, float width, float height, Runnable click) {
        super(positionX, positionY, width, height);
    }

    @Override
    public void render(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer, Camera camera) {

    }

    @Override
    public void clickedComponent(int y, int x) {

    }
}
