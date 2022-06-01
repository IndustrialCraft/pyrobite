package com.github.industrialcraft.pyrobite.ui;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public abstract class UIComponent {

    private UI userInterface;

    public void setUserInterface(UI userInterface) {
        this.userInterface = userInterface;
    }

    public UI getUserInterface() {
        return userInterface;
    }

    public void onKeyTyped(char key) {

    }

    public abstract void render(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer, Camera camera);
}
